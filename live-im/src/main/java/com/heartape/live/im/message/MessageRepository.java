package com.heartape.live.im.message;

/**
 * 消息存储
 * @since 0.0.1
 * @author heartape
 */
public interface MessageRepository<T extends Message> {

    /**
     * 保存
     * @param message 消息
     */
    void save(T message);

    /**
     * 已读回执
     * @param id 消息id
     */
    void receipt(String id);

    /**
     * 撤回
     * @param id 消息id
     */
    void recall(String id);

    /**
     * 删除
     * @param id 消息id
     */
    void remove(String id);

}
