package com.heartape.live.im.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.heartape.exception.SystemInnerException;
import com.heartape.live.im.context.MessageContext;
import com.heartape.live.im.gateway.Gateway;
import com.heartape.live.im.gateway.PurposeType;
import com.heartape.live.im.manage.group.GroupChatMember;
import com.heartape.live.im.manage.group.GroupChatMemberRepository;
import com.heartape.live.im.send.Send;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 本地 websocket session管理
 */
@Slf4j
@AllArgsConstructor
public class StandaloneWebSocketSessionManager implements WebSocketSessionManager {

    private final Gateway gateway;

    private final GroupChatMemberRepository groupChatMemberRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final static Map<String, WebSocketSession> sessionMap = new ConcurrentHashMap<>();

    @Override
    public void register(String uid, WebSocketSession session) {
        sessionMap.put(uid, session);
    }

    @Override
    public boolean registered(String uid) {
        return false;
    }

    @Override
    public StoredMessage store(String uid, String payload) {
        JsonNode jsonNode;
        try {
            jsonNode = this.objectMapper.readTree(payload);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
        String type = jsonNode.get("type").asText();
        JsonNode data = jsonNode.get("data");
        if (PurposeType.PERSON.equals(type)){
            PersonRequest personRequest = objectMapper.convertValue(data, PersonRequest.class);
            return doPerson(uid, personRequest);
        } else if (PurposeType.GROUP.equals(type)){
            GroupRequest groupRequest = objectMapper.convertValue(data, GroupRequest.class);
            return doGroup(uid, groupRequest);
        }
        throw new IllegalArgumentException("illegal type");
    }

    @Override
    public void callback(WebSocketSession session, StoredMessage storedMessage) {
        try {
            session.sendMessage(new TextMessage(storedMessage.getSendStr()));
        } catch (IOException e) {
            throw new SystemInnerException();
        }
    }

    @Override
    public void push(StoredMessage storedMessage) {
        String purposeType = storedMessage.getPurposeType();
        if (PurposeType.PERSON.equals(purposeType)){
            doPush(storedMessage.getPurposeId(), storedMessage.getSendStr());
        } else if (PurposeType.GROUP.equals(purposeType)) {
            groupChatMemberRepository.findByGroupId(storedMessage.getPurposeId())
                    .stream()
                    .map(GroupChatMember::getUid)
                    .forEach(item -> doPush(item, storedMessage.getSendStr()));
        }
    }

    private StoredMessage doPerson(String uid, PersonRequest personRequest){
        String personId = personRequest.getPersonId();
        MessageContext messageContext = MessageContext.builder()
                .uid(uid)
                .purpose(personId)
                .purposeType(PurposeType.PERSON)
                .messageStr(personRequest.getMessage())
                .build();
        Send send = this.gateway.message(messageContext);
        try {
            String sendStr = objectMapper.writeValueAsString(send);
            return new StoredMessage(PurposeType.PERSON, personId, sendStr);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private StoredMessage doGroup(String uid, GroupRequest groupRequest){
        String groupId = groupRequest.getGroupId();
        MessageContext messageContext = MessageContext.builder()
                .uid(uid)
                .purpose(groupId)
                .purposeType(PurposeType.GROUP)
                .messageStr(groupRequest.getMessage())
                .build();
        Send send = this.gateway.message(messageContext);
        try {
            String sendStr = objectMapper.writeValueAsString(send);
            return new StoredMessage(PurposeType.GROUP, groupId, sendStr);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private void doPush(String personId, String data) {
        WebSocketSession webSocketSession = sessionMap.get(personId);
        if (webSocketSession == null || !webSocketSession.isOpen()){
            sessionMap.remove(personId);
            return;
        }
        try {
            webSocketSession.sendMessage(new TextMessage(data));
        } catch (IOException e) {
            throw new SystemInnerException();
        }
    }

    @Override
    public void remove(String uid) {
        sessionMap.remove(uid);
    }


}
