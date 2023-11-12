package com.heartape.live.im.message.type.greeting;

import com.heartape.live.im.mapper.GroupBaseMapper;
import com.heartape.live.im.mapper.GroupGreetingMapper;
import com.heartape.live.im.mapper.entity.GroupEntity;
import com.heartape.live.im.mapper.entity.GroupGreetingEntity;
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
public class JdbcGroupGreetingRepository implements MessageRepository<GreetingMessage> {

    private final GroupGreetingMapper groupGreetingRepository;

    private final GroupBaseMapper groupBaseRepository;

    @Override
    public void save(GreetingMessage message) {
        GroupEntity groupEntity = entity(message);
        groupBaseRepository.insert(groupEntity);
        Greeting greeting = message.getContent();
        GroupGreetingEntity groupGreetingEntity = entity(greeting);
        groupGreetingEntity.setMessageId(groupEntity.getId());
        groupGreetingRepository.insert(groupGreetingEntity);
    }

    public GroupEntity entity(BaseMessage<?> message) {
        return new GroupEntity(null, message.getUid(), message.getPurpose(), message.getType(), 0, message.getTimestamp());
    }

    private GroupGreetingEntity entity(Greeting greeting) {
        return new GroupGreetingEntity(null, null, greeting.getGreetings());
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
