package com.heartape.live.im.message.base;

import com.heartape.live.im.message.MessageRepository;

/**
 * 群聊存储内存实现，仅用于开发测试。
 * 数据权限应从用户加入群聊的时间开始
 * @see MessageRepository
 * @since 0.0.1
 * @author heartape
 */
public class MemoryGroupBaseMessageRepository implements MessageRepository<BaseMessage<?>> {

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
