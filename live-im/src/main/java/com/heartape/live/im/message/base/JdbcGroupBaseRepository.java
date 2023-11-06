package com.heartape.live.im.message.base;

import com.heartape.live.im.jpa.JpaGroupBaseRepository;
import com.heartape.live.im.jpa.entity.GroupEntity;
import com.heartape.live.im.message.MessageRepository;
import lombok.AllArgsConstructor;

/**
 * 基于jdbc的实现，读扩散设计
 * @since 0.0.1
 * @author heartape
 */
@AllArgsConstructor
public class JdbcGroupBaseRepository implements MessageRepository<BaseMessage<?>> {

    private final JpaGroupBaseRepository groupBaseRepository;

    @Override
    public void save(BaseMessage<?> message) {
        GroupEntity groupEntity = entity(message);
        groupBaseRepository.save(groupEntity);
    }

    public GroupEntity entity(BaseMessage<?> message) {
        return new GroupEntity(null, message.uid, message.purpose, message.type, 0, message.timestamp);
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
