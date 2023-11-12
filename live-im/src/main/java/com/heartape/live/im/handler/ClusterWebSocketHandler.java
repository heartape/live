package com.heartape.live.im.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
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
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws JsonProcessingException {
        String payload = message.getPayload();
        JsonNode jsonNode = objectMapper.readTree(payload);
        String purposeType = jsonNode.get("purposeType").asText();
        String purposeId = jsonNode.get("purposeId").asText();
        String sendStr = jsonNode.get("sendStr").asText();
        webSocketSessionManager.push(new WebSocketSessionManager.StoredMessage(purposeType, purposeId, sendStr));
    }
}
