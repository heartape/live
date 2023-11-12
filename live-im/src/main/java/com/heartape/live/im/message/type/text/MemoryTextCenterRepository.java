package com.heartape.live.im.message.type.text;

import com.heartape.live.im.message.MessageRepository;

/**
 * 内存实现
 * @since 0.0.1
 * @author heartape
 * @see MessageRepository
 */
public class MemoryTextCenterRepository implements MessageRepository<TextMessage> {

    @Override
    public void save(TextMessage message) {

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
