package com.heartape.live.im.message.type.sound;

import com.heartape.live.im.jpa.JpaSingleBaseRepository;
import com.heartape.live.im.jpa.JpaSingleSoundRepository;
import com.heartape.live.im.jpa.entity.SingleEntity;
import com.heartape.live.im.jpa.entity.SingleSoundEntity;
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

    private final JpaSingleSoundRepository singleSoundRepository;

    private final JpaSingleBaseRepository singleBaseRepository;

    @Override
    public void save(SoundMessage message) {
        SingleEntity singleEntity = entity(message);
        SingleEntity singleEntitySave = singleBaseRepository.save(singleEntity);
        Sound sound = message.getContent();
        SingleSoundEntity singleSoundEntity = entity(sound);
        singleSoundEntity.setMessageId(singleEntitySave.getId());
        singleSoundRepository.save(singleSoundEntity);
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
