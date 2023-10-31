package com.heartape.live.im.handler;

import com.heartape.exception.PermissionDeniedException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.security.Principal;

/**
 * im聊天处理
 */
@SuppressWarnings("NullableProblems")
@Slf4j
@AllArgsConstructor
public class ImTextWebSocketHandler extends TextWebSocketHandler {

    private final WebSocketSessionManager webSocketSessionManager;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String id = getId(session);
        WebSocketSessionManager.StoredMessage storedMessage = webSocketSessionManager.store(id, message.getPayload());
        webSocketSessionManager.callback(session, storedMessage);
        webSocketSessionManager.push(storedMessage);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String id = getId(session);
        webSocketSessionManager.register(id, session);
        session.sendMessage(new TextMessage("hello world!"));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String id = getId(session);
        webSocketSessionManager.remove(id);
    }

    private String getId(WebSocketSession session) throws IOException {
        Principal principal = session.getPrincipal();
        if (principal == null){
            session.close();
            throw new PermissionDeniedException();
        }
        return principal.getName();
    }
}
