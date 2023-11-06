package com.heartape.live.im.message.type.file;

import com.heartape.live.im.message.MessageRepository;

/**
 * 基于中央仓库的实现
 * @since 0.0.1
 * @author heartape
 * @see MessageRepository
 */
public class MemoryFileCenterRepository implements MessageRepository<FileMessage> {


    @Override
    public void save(FileMessage message) {

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
