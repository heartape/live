package com.heartape.live.im.handler;

import jakarta.annotation.Nonnull;
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
     * @param uid 推送的用户id
     * @param data 推送的数据
     * @return 是否推送成功
     * @throws Exception 异常
     */
    boolean push(String uid, @Nonnull String data);

    /**
     * 移除
     * @param uid 推送的用户id
     */
    void remove(String uid);

}
