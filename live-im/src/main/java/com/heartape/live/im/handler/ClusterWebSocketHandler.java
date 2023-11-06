package com.heartape.live.im.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.net.InetSocketAddress;

/**
 * im聊天处理
 */
@SuppressWarnings("NullableProblems")
@Slf4j
@AllArgsConstructor
public class ClusterWebSocketHandler extends TextWebSocketHandler {

    private final WebSocketSessionManager webSocketSessionManager;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        WebSocketSessionManager.StoredMessage storedMessage = objectMapper.convertValue(objectMapper.readTree(payload), WebSocketSessionManager.StoredMessage.class);
        webSocketSessionManager.push(storedMessage);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        InetSocketAddress remoteAddress = session.getRemoteAddress();
        InetSocketAddress localAddress = session.getLocalAddress();
        String host = (String)session.getAttributes().get("host");
        if(!StringUtils.hasText(host)){
            throw new IllegalArgumentException("host is null");
        }
        webSocketSessionManager.register(host, session);
    }
}
