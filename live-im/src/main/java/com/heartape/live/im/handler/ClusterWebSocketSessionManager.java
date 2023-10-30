package com.heartape.live.im.handler;

import com.heartape.exception.PermissionDeniedException;
import com.heartape.exception.SystemInnerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.Principal;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;

/**
 * 集群 websocket session管理
 * todo: 单用户多连接处理；集群弹性扩容；集群内通信保活
 */
@SuppressWarnings("NullableProblems")
@Slf4j
public class ClusterWebSocketSessionManager extends TextWebSocketHandler implements WebSocketSessionManager {

    private final RedisOperations<String, String> redisOperations;

    private final WebSocketSessionManager standaloneSessionManager;

    private final Set<String> clusters;

    public ClusterWebSocketSessionManager(RedisOperations<String, String> redisOperations, WebSocketSessionManager standaloneSessionManager, Set<String> clusters) {
        this.redisOperations = redisOperations;
        this.standaloneSessionManager = standaloneSessionManager;
        this.clusters = clusters;
        init();
    }

    /**
     * 集群session集合，用于与集群内其他服务通信，host -> WebSocketSession
     */
    private final static Map<String, WebSocketSession> sessionMap = new ConcurrentHashMap<>();

    private final static String WEBSOCKET_USER_PREFIX = "live:ws:u:";

    private void init() {
        log.debug("websocket cluster connecting...");
        clusters.forEach(this::initClusterSession);
    }

    /**
     * 与集群内其他服务建立连接
     * @param cluster 集群host
     */
    private void initClusterSession(String cluster) {
        if (sessionMap.containsKey(cluster)) {
            return;
        }
        StandardWebSocketClient standardWebSocketClient = new StandardWebSocketClient();
        CompletableFuture<WebSocketSession> future = standardWebSocketClient.execute(this, "ws://" + cluster + "/ws/cluster");
        try {
            WebSocketSession webSocketSession = future.get(5, TimeUnit.SECONDS);
            sessionMap.put(cluster, webSocketSession);
            log.info("cluster member: {} connect success", cluster);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            log.warn("cluster member: {} connect failed", cluster);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Map<String, Object> attributes = session.getAttributes();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String id = getId(session);
        register(id, session);
        session.sendMessage(new TextMessage("hello world!"));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String id = getId(session);
        remove(id);
    }

    private String getId(WebSocketSession session) throws IOException {
        Principal principal = session.getPrincipal();
        if (principal == null){
            session.close();
            throw new PermissionDeniedException();
        }
        return principal.getName();
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
    public boolean push(String uid, String data) {
        boolean finish = standaloneSessionManager.push(uid, data);
        if (finish) {
            return true; // 如果单机session推送成功，则直接返回，不再进行集群session推送
        }
        String host = redisOperations.opsForValue().get(WEBSOCKET_USER_PREFIX + uid);
        if (StringUtils.hasText(host)) {
            try {
                sessionMap.get(host).sendMessage(new TextMessage(data));
            } catch (IOException e) {
                throw new SystemInnerException();
            }
            return true;
        }
        return false;
    }

    @Override
    public void remove(String uid) {
        redisOperations.delete(WEBSOCKET_USER_PREFIX + uid);
        standaloneSessionManager.remove(uid);
    }
}
