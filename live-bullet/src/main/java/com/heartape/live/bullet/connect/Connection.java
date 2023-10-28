package com.heartape.live.bullet.connect;

import java.io.IOException;
import java.util.List;

/**
 * 连接基类
 */
public interface Connection {

    String getUid();
    String getRoomId();

    /**
     * 推送
     * @param o 推送内容
     * @param timestamp 时间戳
     */
    void send(Object o, long timestamp);

    /**
     * 断开连接
     */
    void disconnect();

    /**
     * 连接是否已断开
     */
    boolean isCompleted();

}
