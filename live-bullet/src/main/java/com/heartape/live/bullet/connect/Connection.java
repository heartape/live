package com.heartape.live.bullet.connect;

import java.io.IOException;
import java.util.List;

/**
 * 连接基类
 */
public interface Connection<T> {

    String getUid();
    String getRoomId();

    /**
     * 推送
     * @param list 推送内容
     * @param timestamp 时间戳
     * @throws IOException 推送时io异常
     */
    void send(List<T> list, long timestamp) throws IOException;

    /**
     * 断开连接
     */
    void disconnect();

    /**
     * 连接是否已断开
     */
    boolean isCompleted();

}
