package com.heartape.live.im.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heartape.exception.PermissionDeniedException;
import com.heartape.live.im.context.MessageContext;
import com.heartape.live.im.gateway.Gateway;
import com.heartape.live.im.gateway.PurposeType;
import com.heartape.live.im.send.Send;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.security.Principal;

/**
 * 单机版 websocket
 */
@SuppressWarnings("NullableProblems")
@Slf4j
@AllArgsConstructor
public class ClusterWebSocketHandler extends TextWebSocketHandler implements WebSocketSessionManager {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        PersonRequest personRequest = objectMapper.convertValue(message.getPayload(), PersonRequest.class);
        String id = getId(session);
        String personId = personRequest.getPersonId();
        MessageContext messageContext = MessageContext.builder()
                .uid(id)
                .purpose(personId)
                .purposeType(PurposeType.USER)
                .messageStr(personRequest.getMessage())
                .build();
        Send send = this.gateway.message(messageContext);
        String data = objectMapper.writeValueAsString(send);
        session.sendMessage(new TextMessage(data));
        push(personId, data);
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

    }

    @Override
    public boolean push(String uid, String data) throws Exception {
        return false;
    }

    @Override
    public void remove(String uid) {

    }
}
