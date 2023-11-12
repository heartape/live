package com.heartape.live.im.message.type.sound;

import com.heartape.live.im.message.MessageRepository;

/**
 * 基于中央仓库的实现
 * @since 0.0.1
 * @author heartape
 * @see MessageRepository
 */
public class MemorySoundCenterRepository implements MessageRepository<SoundMessage> {

    @Override
    public void save(SoundMessage message) {

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
