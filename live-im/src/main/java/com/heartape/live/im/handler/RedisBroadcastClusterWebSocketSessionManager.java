package com.heartape.live.im.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.heartape.exception.SystemInnerException;
import com.heartape.live.im.config.RedisBroadcastConfiguration;
import com.heartape.live.im.gateway.PurposeType;
import com.heartape.live.im.manage.group.GroupChatMember;
import com.heartape.live.im.manage.group.GroupChatMemberRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.web.socket.WebSocketSession;

import java.io.Serializable;
import java.util.Map;

/**
 * 集群 websocket session管理,利用redis广播，需要打开 {@link RedisBroadcastConfiguration}
 * todo: 单用户多连接处理
 */
@Slf4j
@AllArgsConstructor
public class RedisBroadcastClusterWebSocketSessionManager implements WebSocketSessionManager {

    private final RedisOperations<String, String> redisOperations;

    private final WebSocketSessionManager standaloneSessionManager;

    private final GroupChatMemberRepository groupChatMemberRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public final static String WEBSOCKET_USER_BROADCAST = "live:ws:u:bc";

    @Override
    public void register(String uid, WebSocketSession session) {
        standaloneSessionManager.register(uid, session);
    }

    @Override
    public boolean registered(String uid) {
        return true;
    }

    @Override
    public StoredMessage store(String uid, String payload) {
        return standaloneSessionManager.store(uid, payload);
    }

    @Override
    public void callback(WebSocketSession session, StoredMessage storedMessage) {
        standaloneSessionManager.callback(session, storedMessage);
    }

    @Override
    public void push(StoredMessage storedMessage) {
        String purposeType = storedMessage.getPurposeType();
        String storedMessageStr;
        try {
            storedMessageStr = objectMapper.writeValueAsString(storedMessage);
        } catch (JsonProcessingException e) {
            throw new SystemInnerException();
        }
        if (PurposeType.PERSON.equals(purposeType)){
            doPush(storedMessage.getPurposeId(), storedMessage, storedMessageStr);
        } else if (PurposeType.GROUP.equals(purposeType)) {
            groupChatMemberRepository.findByGroupId(storedMessage.getPurposeId())
                    .stream()
                    .map(GroupChatMember::getUid)
                    .forEach(item -> doPush(item, storedMessage, storedMessageStr));
        }
    }

    private void doPush(String purposeId, StoredMessage storedMessage, String storedMessageStr) {
        boolean registered = standaloneSessionManager.registered(purposeId);
        if (registered) {
            standaloneSessionManager.push(storedMessage);
            return;
        }
        redisOperations.convertAndSend(WEBSOCKET_USER_BROADCAST, storedMessageStr);
    }

    @Override
    public void remove(String uid) {
        standaloneSessionManager.remove(uid);
    }


    @AllArgsConstructor
    public static class DefaultMessageDelegate implements RedisBroadcastConfiguration.MessageDelegate {

        private final WebSocketSessionManager standaloneSessionManager;

        private final ObjectMapper objectMapper = new ObjectMapper();

        @Override
        public void handleMessage(String message) {
            try {
                StoredMessage storedMessage = objectMapper.convertValue(objectMapper.readTree(message), StoredMessage.class);
                if (standaloneSessionManager.registered(storedMessage.getPurposeId())){
                    standaloneSessionManager.push(storedMessage);
                }
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void handleMessage(Map message) {

        }

        @Override
        public void handleMessage(byte[] message) {

        }

        @Override
        public void handleMessage(Serializable message) {
            try {
                StoredMessage storedMessage = objectMapper.convertValue(objectMapper.readTree(message.toString()), StoredMessage.class);
                if (standaloneSessionManager.registered(storedMessage.getPurposeId())){
                    standaloneSessionManager.push(storedMessage);
                }
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void handleMessage(Serializable message, String channel) {

        }
    }
}
