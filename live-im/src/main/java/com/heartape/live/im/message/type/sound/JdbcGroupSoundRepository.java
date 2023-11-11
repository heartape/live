package com.heartape.live.im.message.type.sound;

import com.heartape.live.im.mapper.GroupBaseMapper;
import com.heartape.live.im.mapper.GroupSoundMapper;
import com.heartape.live.im.mapper.entity.GroupEntity;
import com.heartape.live.im.mapper.entity.GroupSoundEntity;
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

    private final GroupSoundMapper groupSoundRepository;

    private final GroupBaseMapper groupBaseRepository;

    @Override
    public void save(SoundMessage message) {
        GroupEntity groupEntity = entity(message);
        groupBaseRepository.insert(groupEntity);
        Sound sound = message.getContent();
        GroupSoundEntity groupSoundEntity = entity(sound);
        groupSoundEntity.setMessageId(groupEntity.getId());
        groupSoundRepository.insert(groupSoundEntity);
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
