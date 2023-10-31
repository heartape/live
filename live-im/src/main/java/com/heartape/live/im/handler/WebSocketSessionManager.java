package com.heartape.live.im.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.socket.WebSocketSession;

/**
 * websocket session管理
 */
public interface WebSocketSessionManager {

    /**
     * 注册
     * @param uid 推送的用户id
     * @param session WebSocketSession
     */
    void register(String uid, WebSocketSession session);

    /**
     * 已注册
     * @param uid 推送的用户id
     * @return 是否已注册
     */
    boolean registered(String uid);

    /**
     * 存储
     * @param uid 推送的用户id
     * @param payload 推送的数据
     */
    StoredMessage store(String uid, String payload);

    /**
     * 回调
     * @param session WebSocketSession
     * @param storedMessage StoredMessage
     */
    void callback(WebSocketSession session, StoredMessage storedMessage);

    /**
     * 推送
     * @param storedMessage StoredMessage
     */
    void push(StoredMessage storedMessage);

    /**
     * 移除
     * @param uid 推送的用户id
     */
    void remove(String uid);

    @Getter
    @AllArgsConstructor
    class StoredMessage {
        private String purposeType;
        private String purposeId;
        private String sendStr;
    }

}
