package com.heartape.live.im.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.heartape.exception.PermissionDeniedException;
import com.heartape.live.im.context.MessageContext;
import com.heartape.live.im.gateway.Gateway;
import com.heartape.live.im.gateway.PurposeType;
import com.heartape.live.im.manage.group.GroupChatService;
import com.heartape.live.im.manage.group.info.GroupChatMemberInfo;
import com.heartape.live.im.send.Send;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

/**
 * im聊天处理
 */
@SuppressWarnings("NullableProblems")
@Slf4j
@AllArgsConstructor
public class ImTextWebSocketHandler extends TextWebSocketHandler {

    private final Gateway gateway;

    private final WebSocketSessionManager webSocketSessionManager;

    private final GroupChatService groupChatService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        JsonNode jsonNode = this.objectMapper.readTree(message.getPayload());
        String type = jsonNode.get("type").asText();
        JsonNode data = jsonNode.get("data");
        if ("person".equals(type)){
            PersonRequest personRequest = objectMapper.convertValue(data, PersonRequest.class);
            doPerson(session, personRequest);
        } else if ("group".equals(type)){
            GroupRequest groupRequest = objectMapper.convertValue(data, GroupRequest.class);
            doGroup(session, groupRequest);
        }

    }

    private void doPerson(WebSocketSession session, PersonRequest personRequest) throws Exception{
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
        webSocketSessionManager.push(personId, data);
    }

    private void doGroup(WebSocketSession session, GroupRequest groupRequest) throws Exception{
        String groupId = groupRequest.getGroupId();
        MessageContext messageContext = MessageContext.builder()
                .uid(groupRequest.getUid())
                .purpose(groupId)
                .purposeType(PurposeType.GROUP)
                .messageStr(groupRequest.getMessage())
                .build();
        Send send = this.gateway.message(messageContext);
        String data = objectMapper.writeValueAsString(send);
        session.sendMessage(new TextMessage(data));
        groupMembers(groupId).forEach(uid -> {
            try {
                webSocketSessionManager.push(uid, data);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private List<String> groupMembers(String groupId){
        return groupChatService.getGroupChatMemberList(groupId)
                .stream()
                .map(GroupChatMemberInfo::getUid)
                .toList();
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
