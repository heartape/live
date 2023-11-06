package com.heartape.live.im.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

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
}
