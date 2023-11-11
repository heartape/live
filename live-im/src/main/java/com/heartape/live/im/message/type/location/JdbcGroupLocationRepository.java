package com.heartape.live.im.message.type.location;

import com.heartape.live.im.mapper.GroupBaseMapper;
import com.heartape.live.im.mapper.GroupLocationMapper;
import com.heartape.live.im.mapper.entity.GroupEntity;
import com.heartape.live.im.mapper.entity.GroupLocationEntity;
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

    private final GroupLocationMapper groupLocationRepository;

    private final GroupBaseMapper groupBaseRepository;

    @Override
    public void save(LocationMessage message) {
        GroupEntity groupEntity = entity(message);
        groupBaseRepository.insert(groupEntity);
        Location location = message.getContent();
        GroupLocationEntity groupLocationEntity = entity(location);
        groupLocationEntity.setMessageId(groupEntity.getId());
        groupLocationRepository.insert(groupLocationEntity);
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
