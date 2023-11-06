package com.heartape.live.im.message.base;

import com.heartape.live.im.jpa.JpaSingleBaseRepository;
import com.heartape.live.im.jpa.entity.SingleEntity;
import com.heartape.live.im.message.MessageRepository;
import lombok.AllArgsConstructor;

/**
 * 基于jdbc的实现，读扩散设计
 * @since 0.0.1
 * @author heartape
 * @see MessageRepository
 */
@AllArgsConstructor
public class JdbcSingleBaseRepository implements MessageRepository<BaseMessage<?>> {

    private final JpaSingleBaseRepository singleBaseRepository;

    @Override
    public void save(BaseMessage<?> message) {
        SingleEntity singleEntity = entity(message);
        singleBaseRepository.save(singleEntity);
    }

    public SingleEntity entity(BaseMessage<?> message) {
        return new SingleEntity(null, message.uid, message.purpose, message.type, false, message.timestamp);
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
