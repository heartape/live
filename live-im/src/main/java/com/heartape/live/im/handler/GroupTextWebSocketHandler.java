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
public class GroupTextWebSocketHandler extends TextWebSocketHandler {

    private final Gateway gateway;

    private final Map<String, WebSocketSession> sessionMap = new ConcurrentHashMap<>();

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        GroupRequest groupRequest = objectMapper.convertValue(message.getPayload(), GroupRequest.class);
        MessageContext messageContext = MessageContext.builder()
                .uid(groupRequest.getUid())
                .purpose(groupRequest.getGroupId())
                .purposeType(PurposeType.GROUP)
                .messageStr(groupRequest.getMessage())
                .build();
        Send send = this.gateway.message(messageContext);
        String data = objectMapper.writeValueAsString(send);
        session.sendMessage(new TextMessage(data));
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

    }
}
