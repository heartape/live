package com.heartape.live.im.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.security.Principal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("NullableProblems")
@Slf4j
@AllArgsConstructor
public class PersonTextWebSocketHandler extends TextWebSocketHandler {

    private final Gateway gateway;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final static Map<String, WebSocketSession> sessionMap = new ConcurrentHashMap<>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        PersonRequest personRequest = objectMapper.convertValue(message.getPayload(), PersonRequest.class);
        MessageContext messageContext = MessageContext.builder()
                .uid(personRequest.getUid())
                .purpose(personRequest.getPersonId())
                .purposeType(PurposeType.USER)
                .messageStr(personRequest.getMessage())
                .build();
        Send send = this.gateway.message(messageContext);
        String data = objectMapper.writeValueAsString(send);
        session.sendMessage(new TextMessage(data));
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Principal principal = session.getPrincipal();
        if (principal == null){
            session.close();
            return;
        }
        String id = principal.getName();
        sessionMap.put(id, session);
        session.sendMessage(new TextMessage("hello world!"));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

    }
}
