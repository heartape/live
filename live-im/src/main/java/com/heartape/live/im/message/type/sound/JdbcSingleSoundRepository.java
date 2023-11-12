package com.heartape.live.im.message.type.sound;

import com.heartape.live.im.mapper.SingleBaseMapper;
import com.heartape.live.im.mapper.SingleSoundMapper;
import com.heartape.live.im.mapper.entity.SingleEntity;
import com.heartape.live.im.mapper.entity.SingleSoundEntity;
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
public class JdbcSingleSoundRepository implements MessageRepository<SoundMessage> {

    private final SingleSoundMapper singleSoundRepository;

    private final SingleBaseMapper singleBaseRepository;

    @Override
    public void save(SoundMessage message) {
        SingleEntity singleEntity = entity(message);
        singleBaseRepository.insert(singleEntity);
        Sound sound = message.getContent();
        SingleSoundEntity singleSoundEntity = entity(sound);
        singleSoundEntity.setMessageId(singleEntity.getId());
        singleSoundRepository.insert(singleSoundEntity);
    }

    public SingleEntity entity(BaseMessage<?> message) {
        return new SingleEntity(null, message.getUid(), message.getPurpose(), message.getType(), false, message.getTimestamp());
    }

    private SingleSoundEntity entity(Sound sound) {
        return new SingleSoundEntity(null, null, sound.getUrl(), sound.getSize(), sound.getSecond());
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
