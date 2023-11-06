package com.heartape.live.im.message.type.greeting;

import com.heartape.live.im.message.*;

/**
 * 基于中央仓库的实现
 * @since 0.0.1
 * @author heartape
 * @see MessageRepository
 */
public class MemoryGreetingCenterRepository implements MessageRepository<GreetingMessage> {

    @Override
    public void save(GreetingMessage message) {

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
