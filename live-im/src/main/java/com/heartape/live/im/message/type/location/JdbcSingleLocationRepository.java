package com.heartape.live.im.message.type.location;

import com.heartape.live.im.jpa.JpaSingleBaseRepository;
import com.heartape.live.im.jpa.JpaSingleLocationRepository;
import com.heartape.live.im.jpa.entity.SingleEntity;
import com.heartape.live.im.jpa.entity.SingleLocationEntity;
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
public class JdbcSingleLocationRepository implements MessageRepository<LocationMessage> {

    private final JpaSingleLocationRepository singleLocationRepository;

    private final JpaSingleBaseRepository singleBaseRepository;

    @Override
    public void save(LocationMessage message) {
        SingleEntity singleEntity = entity(message);
        SingleEntity singleEntitySave = singleBaseRepository.save(singleEntity);
        Location location = message.getContent();
        SingleLocationEntity singleLocationEntity = entity(location);
        singleLocationEntity.setMessageId(singleEntitySave.getId());
        singleLocationRepository.save(singleLocationEntity);
    }

    public SingleEntity entity(BaseMessage<?> message) {
        return new SingleEntity(null, message.getUid(), message.getPurpose(), message.getType(), false, message.getTimestamp());
    }

    private SingleLocationEntity entity(Location location) {
        return new SingleLocationEntity(null, null, location.getPoint(), location.getDesc());
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
