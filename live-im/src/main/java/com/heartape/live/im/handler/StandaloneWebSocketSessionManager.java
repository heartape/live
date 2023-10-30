package com.heartape.live.im.handler;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 本地 websocket session管理
 */
public class StandaloneWebSocketSessionManager implements WebSocketSessionManager {

    private final static Map<String, WebSocketSession> sessionMap = new ConcurrentHashMap<>();

    @Override
    public void register(String uid, WebSocketSession session) {
        sessionMap.put(uid, session);
    }

    @Override
    public boolean push(String uid, String data) throws Exception {
        WebSocketSession webSocketSession = sessionMap.get(uid);
        if (webSocketSession == null || !webSocketSession.isOpen()){
            sessionMap.remove(uid);
            return false;
        }
        webSocketSession.sendMessage(new TextMessage(data));
        return true;
    }

    @Override
    public void remove(String uid) {
        sessionMap.remove(uid);
    }
}
