package com.heartape.live.im.message.base;

import com.heartape.live.im.message.MessageRepository;

/**
 * 私聊存储内存实现，仅用于开发测试
 * @see MessageRepository
 * @since 0.0.1
 * @author heartape
 */
public class MemorySingleBaseMessageRepository implements MessageRepository<BaseMessage<?>> {

    @Override
    public void save(BaseMessage<?> message) {

    }

    @Override
    public void receipt(String id) {

    }

    @Override
    public void recall(String id) {

    }

    @Override
    public void remove(String id) {

    }
}
