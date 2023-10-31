package com.heartape.live.im.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.heartape.exception.SystemInnerException;
import com.heartape.live.im.gateway.PurposeType;
import com.heartape.live.im.manage.group.GroupChatMember;
import com.heartape.live.im.manage.group.GroupChatMemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
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

    private final String host;

    public ClusterWebSocketSessionManager(RedisOperations<String, String> redisOperations, WebSocketSessionManager standaloneSessionManager, WebSocketHandler webSocketHandler, GroupChatMemberRepository groupChatMemberRepository, Set<String> servers, String host) {
        this.redisOperations = redisOperations;
        this.standaloneSessionManager = standaloneSessionManager;
        this.webSocketHandler = webSocketHandler;
        this.groupChatMemberRepository = groupChatMemberRepository;
        this.servers = servers;
        this.host = host;
        init();
    }

    /**
     * 集群session集合，用于与集群内其他服务通信，host -> WebSocketSession
     */
    private final static Map<String, WebSocketSession> sessionMap = new ConcurrentHashMap<>();

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final static String WEBSOCKET_USER_PREFIX = "live:ws:u:";

    private void init() {
        log.debug("websocket cluster connecting...");
        servers.forEach(this::initClusterSession);
    }

    /**
     * 与集群内其他服务建立连接
     * @param server 集群host
     */
    private void initClusterSession(String server) {
        if (sessionMap.containsKey(server) || Objects.equals(server, host)) {
            return;
        }
        StandardWebSocketClient standardWebSocketClient = new StandardWebSocketClient();
        CompletableFuture<WebSocketSession> future = standardWebSocketClient.execute(webSocketHandler, "ws://" + server + "/cluster");
        try {
            WebSocketSession webSocketSession = future.get(5, TimeUnit.SECONDS);
            sessionMap.put(server, webSocketSession);
            log.info("cluster server: {} connect success", server);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            log.warn("cluster server: {} connect failed", server);
        }
    }

    @Override
    public void register(String uid, WebSocketSession session) {
        standaloneSessionManager.register(uid, session);
        String host;
        try {
            host = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw new SystemInnerException();
        }
        redisOperations.opsForValue().set(WEBSOCKET_USER_PREFIX + uid, host);
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
        if (StringUtils.hasText(host)) {
            try {
                // todo:断线重连
                sessionMap.get(host).sendMessage(new TextMessage(storedMessageStr));
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
