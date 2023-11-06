package com.heartape.live.im.message.type.image;

import com.heartape.live.im.message.MessageRepository;

/**
 * 基于中央仓库的实现
 * @since 0.0.1
 * @author heartape
 * @see MessageRepository
 */
public class MemoryImageCenterRepository implements MessageRepository<ImageMessage> {

    @Override
    public void save(ImageMessage message) {

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
