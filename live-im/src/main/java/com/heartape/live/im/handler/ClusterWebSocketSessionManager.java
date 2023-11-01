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
import java.util.Set;
import java.util.concurrent.*;

/**
 * 集群 websocket session管理
 * todo: 单用户多连接处理；集群弹性扩容；集群内通信保活
 */
@Slf4j
public class ClusterWebSocketSessionManager implements WebSocketSessionManager {

    private final RedisOperations<String, String> redisOperations;

    private final WebSocketSessionManager standaloneSessionManager;

    private final WebSocketHandler webSocketHandler;

    private final GroupChatMemberRepository groupChatMemberRepository;

    private final Set<String> servers;

    private final int port;

    @Setter
    private String networkInterfaceName;

    private String host;

    public ClusterWebSocketSessionManager(RedisOperations<String, String> redisOperations, WebSocketSessionManager standaloneSessionManager, WebSocketHandler webSocketHandler, GroupChatMemberRepository groupChatMemberRepository, Set<String> servers, int port, String networkInterfaceName) {
        this.redisOperations = redisOperations;
        this.standaloneSessionManager = standaloneSessionManager;
        this.webSocketHandler = webSocketHandler;
        this.groupChatMemberRepository = groupChatMemberRepository;
        this.servers = servers;
        this.port = port;
        this.networkInterfaceName = networkInterfaceName;
        this.host = host();
        if (host == null) {
            log.error("ip地址获取异常");
            throw new CanNotFindHostException();
        }
        init();
    }

    /**
     * 集群session集合，用于与集群内其他服务通信，host -> WebSocketSession
     */
    private final static Map<String, WebSocketSession> sessionMap = new ConcurrentHashMap<>();

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final static String WEBSOCKET_USER_PREFIX = "live:ws:u:";

    private String host() {
        InetAddress inetAddress = IpUtils.localAddressV4(networkInterfaceName);
        if (inetAddress == null) {
            return null;
        }
        return inetAddress.getHostAddress() + ":" + port;
    }

    private void init() {
        log.debug("websocket cluster connecting...");
        servers.forEach(this::initClusterSession);
    }

    /**
     * 与集群内其他服务建立连接
     * @param server 集群host
     */
    private WebSocketSession initClusterSession(String server) {
        if (sessionMap.containsKey(server) || Objects.equals(server, host)) {
            return sessionMap.get(server);
        }
        StandardWebSocketClient standardWebSocketClient = new StandardWebSocketClient();
        CompletableFuture<WebSocketSession> future = standardWebSocketClient.execute(webSocketHandler, "ws://" + server + "/cluster");
        try {
            WebSocketSession webSocketSession = future.get(2, TimeUnit.SECONDS);
            sessionMap.put(server, webSocketSession);
            log.info("cluster server: {} connect success", server);
            return webSocketSession;
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            log.warn("cluster server: {} connect failed", server);
            return null;
        }
    }

    @Override
    public void register(String uid, WebSocketSession session) {
        standaloneSessionManager.register(uid, session);
        String host = host();
        if (host != null) {
            this.host = host;
        }
        if (this.host == null) {
            log.error("ip地址获取异常,无法向ws集群注册");
            return;
        }
        log.info("register uid: {}, host: {}", uid, this.host);
        redisOperations.opsForValue().set(WEBSOCKET_USER_PREFIX + uid, this.host);
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
                WebSocketSession webSocketSessionNew = initClusterSession(host);
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
