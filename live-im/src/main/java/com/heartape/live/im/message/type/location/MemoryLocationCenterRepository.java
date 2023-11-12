package com.heartape.live.im.message.type.location;

import com.heartape.live.im.message.MessageRepository;

/**
 * 基于中央仓库的实现
 * @since 0.0.1
 * @author heartape
 * @see MessageRepository
 */
public class MemoryLocationCenterRepository implements MessageRepository<LocationMessage> {

    @Override
    public void save(LocationMessage message) {

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
