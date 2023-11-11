package com.heartape.live.im.message.type.greeting;

import com.heartape.live.im.mapper.SingleBaseMapper;
import com.heartape.live.im.mapper.SingleGreetingMapper;
import com.heartape.live.im.mapper.entity.SingleEntity;
import com.heartape.live.im.mapper.entity.SingleGreetingEntity;
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

    private final SingleGreetingMapper singleGreetingRepository;

    private final SingleBaseMapper singleBaseRepository;
    @Override
    public void save(GreetingMessage message) {
        SingleEntity singleEntity = entity(message);
        singleBaseRepository.insert(singleEntity);
        Greeting greeting = message.getContent();
        SingleGreetingEntity singleGreetingEntity = entity(greeting);
        singleGreetingEntity.setMessageId(singleEntity.getId());
        singleGreetingRepository.insert(singleGreetingEntity);
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
