package com.heartape.live.im.message.type.video;

import com.heartape.live.im.message.MessageRepository;

/**
 * 基于中央仓库的实现
 * @since 0.0.1
 * @author heartape
 * @see MessageRepository
 */
public class MemoryVideoCenterRepository implements MessageRepository<VideoMessage> {

    @Override
    public void save(VideoMessage message) {

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
