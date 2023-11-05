package com.heartape.live.im.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heartape.exception.SystemInnerException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.net.URI;

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
    public void afterConnectionEstablished(WebSocketSession session) throws IOException {
        String host = getHost(session);
        // todo: 注册server间连接，与用户连接需要区分
        webSocketSessionManager.register(host, session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws IOException {
        String host = getHost(session);
        webSocketSessionManager.remove(host);
    }

    private String getHost(WebSocketSession session) throws IOException {
        URI uri = session.getUri();
        if (uri == null) {
            log.error("uri is null");
            session.close();
            throw new SystemInnerException();
        }
        String host = uri.getAuthority();
        if (!StringUtils.hasText(host)) {
            log.error("host is null");
            session.close();
            throw new SystemInnerException();
        }
        return host;
    }
}
