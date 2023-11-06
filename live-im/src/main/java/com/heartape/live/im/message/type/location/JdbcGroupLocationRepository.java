package com.heartape.live.im.message.type.location;

import com.heartape.live.im.jpa.JpaGroupBaseRepository;
import com.heartape.live.im.jpa.JpaGroupLocationRepository;
import com.heartape.live.im.jpa.entity.GroupEntity;
import com.heartape.live.im.jpa.entity.GroupLocationEntity;
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
public class JdbcGroupLocationRepository implements MessageRepository<LocationMessage> {

    private final JpaGroupLocationRepository groupLocationRepository;

    private final JpaGroupBaseRepository groupBaseRepository;

    @Override
    public void save(LocationMessage message) {
        GroupEntity groupEntity = entity(message);
        GroupEntity groupEntitySave = groupBaseRepository.save(groupEntity);
        Location location = message.getContent();
        GroupLocationEntity groupLocationEntity = entity(location);
        groupLocationEntity.setMessageId(groupEntitySave.getId());
        groupLocationRepository.save(groupLocationEntity);
    }

    public GroupEntity entity(BaseMessage<?> message) {
        return new GroupEntity(null, message.getUid(), message.getPurpose(), message.getType(), 0, message.getTimestamp());
    }

    private GroupLocationEntity entity(Location location) {
        return new GroupLocationEntity(null, null, location.getPoint(), location.getDesc());
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
