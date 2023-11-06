package com.heartape.live.im.message.type.greeting;

import com.heartape.live.im.jpa.JpaSingleBaseRepository;
import com.heartape.live.im.jpa.JpaSingleGreetingRepository;
import com.heartape.live.im.jpa.entity.SingleEntity;
import com.heartape.live.im.jpa.entity.SingleGreetingEntity;
import com.heartape.live.im.message.MessageRepository;
import com.heartape.live.im.message.base.BaseMessage;
import lombok.AllArgsConstructor;

/**
 * 基于jdbc的实现
 * @since 0.0.1
 * @author heartape
 * @see MessageRepository
 */
@AllArgsConstructor
public class JdbcSingleGreetingRepository implements MessageRepository<GreetingMessage> {

    private final JpaSingleGreetingRepository singleGreetingRepository;

    private final JpaSingleBaseRepository singleBaseRepository;
    @Override
    public void save(GreetingMessage message) {
        SingleEntity singleEntity = entity(message);
        SingleEntity singleEntitySave = singleBaseRepository.save(singleEntity);
        Greeting greeting = message.getContent();
        SingleGreetingEntity singleGreetingEntity = entity(greeting);
        singleGreetingEntity.setMessageId(singleEntitySave.getId());
        singleGreetingRepository.save(singleGreetingEntity);
    }

    public SingleEntity entity(BaseMessage<?> message) {
        return new SingleEntity(null, message.getUid(), message.getPurpose(), message.getType(), false, message.getTimestamp());
    }

    private SingleGreetingEntity entity(Greeting greeting) {
        return new SingleGreetingEntity(null, null, greeting.getGreetings());
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
