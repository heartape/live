package com.heartape.live.im.message.type.sound;

import com.heartape.live.im.jpa.JpaGroupBaseRepository;
import com.heartape.live.im.jpa.JpaGroupSoundRepository;
import com.heartape.live.im.jpa.entity.GroupEntity;
import com.heartape.live.im.jpa.entity.GroupSoundEntity;
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
public class JdbcGroupSoundRepository implements MessageRepository<SoundMessage> {

    private final JpaGroupSoundRepository groupSoundRepository;

    private final JpaGroupBaseRepository groupBaseRepository;

    @Override
    public void save(SoundMessage message) {
        GroupEntity groupEntity = entity(message);
        GroupEntity groupEntitySave = groupBaseRepository.save(groupEntity);
        Sound sound = message.getContent();
        GroupSoundEntity groupSoundEntity = entity(sound);
        groupSoundEntity.setMessageId(groupEntitySave.getId());
        groupSoundRepository.save(groupSoundEntity);
    }

    public GroupEntity entity(BaseMessage<?> message) {
        return new GroupEntity(null, message.getUid(), message.getPurpose(), message.getType(), 0, message.getTimestamp());
    }

    private GroupSoundEntity entity(Sound sound) {
        return new GroupSoundEntity(null, null, sound.getUrl(), sound.getSize(), sound.getSecond());
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
