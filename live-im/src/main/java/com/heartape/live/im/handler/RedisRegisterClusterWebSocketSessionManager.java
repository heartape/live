package com.heartape.live.im.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.heartape.exception.CanNotFindHostException;
import com.heartape.exception.SystemInnerException;
import com.heartape.live.im.gateway.PurposeType;
import com.heartape.live.im.manage.group.GroupChatMember;
import com.heartape.live.im.manage.group.GroupChatMemberRepository;
import com.heartape.util.IpUtils;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.*;

/**
 * 集群 websocket session管理
 * todo: 单用户多连接处理；集群弹性扩容；集群内通信保活
 */
@Slf4j
public class RedisRegisterClusterWebSocketSessionManager implements WebSocketSessionManager {

    private final RedisOperations<String, String> redisOperations;

    private final WebSocketSessionManager standaloneSessionManager;

    private final WebSocketHandler webSocketHandler;

    private final GroupChatMemberRepository groupChatMemberRepository;

    private final int port;

    @Setter
    private String networkInterfaceName;

    /**
     * ip:port
     */
    private String host;

    /**
     * 是否预设ip
     */
    private final boolean presets;

    public RedisRegisterClusterWebSocketSessionManager(RedisOperations<String, String> redisOperations, WebSocketSessionManager standaloneSessionManager, WebSocketHandler webSocketHandler, GroupChatMemberRepository groupChatMemberRepository, int port, String host, String networkInterfaceName) {
        this.redisOperations = redisOperations;
        this.standaloneSessionManager = standaloneSessionManager;
        this.webSocketHandler = webSocketHandler;
        this.groupChatMemberRepository = groupChatMemberRepository;
        this.port = port;
        this.networkInterfaceName = networkInterfaceName;
        this.presets = StringUtils.hasText(host);
        if (this.presets){
            this.host = host + ":" + port;
        } else {
            this.host = host();
            if (this.host == null) {
                log.error("ip地址获取异常");
                throw new CanNotFindHostException();
            }
        }
    }

    /**
     * 集群session集合，用于与集群内其他服务通信，host -> WebSocketSession
     */
    private final static Map<String, WebSocketSession> sessionMap = new ConcurrentHashMap<>();

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final static String WEBSOCKET_USER_PREFIX = "live:ws:u:";

    private String host() {
        if (presets){
            return host;
        } else {
            InetAddress inetAddress;
            if (StringUtils.hasText(networkInterfaceName)){
                inetAddress = IpUtils.localAddressV4(networkInterfaceName);
            } else {
                inetAddress = IpUtils.localAddressV4();
            }
            if (inetAddress == null) {
                return null;
            }
            return inetAddress.getHostAddress() + ":" + port;
        }
    }

    /**
     * 与集群内其他服务建立连接
     * @param server 集群host
     */
    private WebSocketSession connect(String server) {
        if (sessionMap.containsKey(server) || Objects.equals(server, host)) {
            return sessionMap.get(server);
        }
        StandardWebSocketClient standardWebSocketClient = new StandardWebSocketClient();
        Map<String, Object> config = Map.of("host", host);
        standardWebSocketClient.setUserProperties(config);
        CompletableFuture<WebSocketSession> future = standardWebSocketClient.execute(webSocketHandler, "ws://" + server + "/cluster");
        try {
            WebSocketSession webSocketSession = future.get(2, TimeUnit.SECONDS);
            sessionMap.put(server, webSocketSession);
            log.info("cluster server: {} connect success", server);
            return webSocketSession;
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            log.warn("cluster server: {} connect failed", server, e);
            return null;
        }
    }

    @Override
    public void register(String uid, WebSocketSession session) {
        standaloneSessionManager.register(uid, session);
        String host;
        if (presets){
            host = this.host;
        } else {
            host = host();
            if (host != null) {
                this.host = host;
            }
        }
        if (host == null) {
            log.error("ip地址获取异常,无法向ws集群注册");
            return;
        }
        log.info("register uid: {}, host: {}", uid, this.host);
        redisOperations.opsForValue().set(WEBSOCKET_USER_PREFIX + uid, this.host, 5, TimeUnit.HOURS);
    }

    @Override
    public boolean registered(String uid) {
        boolean registered = standaloneSessionManager.registered(uid);
        if (registered) {
            return true;
        }
        Boolean hasKey = redisOperations.hasKey(WEBSOCKET_USER_PREFIX + uid);
        return hasKey != null && hasKey;
    }

    @Override
    public StoredMessage store(String uid, String payload) {
        return standaloneSessionManager.store(uid, payload);
    }

    @Override
    public void callback(WebSocketSession session, StoredMessage storedMessage) {
        standaloneSessionManager.callback(session, storedMessage);
    }

    @Override
    public void push(StoredMessage storedMessage) {
        String purposeType = storedMessage.getPurposeType();
        String storedMessageStr;
        try {
            storedMessageStr = objectMapper.writeValueAsString(storedMessage);
        } catch (JsonProcessingException e) {
            throw new SystemInnerException();
        }
        if (PurposeType.PERSON.equals(purposeType)){
            doPush(storedMessage.getPurposeId(), storedMessage, storedMessageStr);
        } else if (PurposeType.GROUP.equals(purposeType)) {
            groupChatMemberRepository.findByGroupId(storedMessage.getPurposeId())
                    .stream()
                    .map(GroupChatMember::getUid)
                    .forEach(item -> doPush(item, storedMessage, storedMessageStr));
        }
    }

    private void doPush(String purposeId, StoredMessage storedMessage, String storedMessageStr) {
        boolean registered = standaloneSessionManager.registered(purposeId);
        if (registered) {
            standaloneSessionManager.push(storedMessage);
            return;
        }
        String host = redisOperations.opsForValue().get(WEBSOCKET_USER_PREFIX + purposeId);
        if (StringUtils.hasText(host) && !Objects.equals(host, this.host)) {
            // 断线重连
            WebSocketSession webSocketSession = sessionMap.get(host);
            if (webSocketSession == null || !webSocketSession.isOpen()) {
                WebSocketSession webSocketSessionNew = connect(host);
                if (webSocketSessionNew == null || !webSocketSessionNew.isOpen()) {
                    return; // 断线重连失败
                }
                webSocketSession = webSocketSessionNew;
            }
            try {
                webSocketSession.sendMessage(new TextMessage(storedMessageStr));
            } catch (IOException e) {
                throw new SystemInnerException();
            }
        }
    }

    @Override
    public void remove(String uid) {
        redisOperations.delete(WEBSOCKET_USER_PREFIX + uid);
        standaloneSessionManager.remove(uid);
    }
}
