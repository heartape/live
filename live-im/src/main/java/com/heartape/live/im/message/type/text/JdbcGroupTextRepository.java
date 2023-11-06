package com.heartape.live.im.message.type.text;

import com.heartape.live.im.jpa.JpaGroupBaseRepository;
import com.heartape.live.im.jpa.JpaGroupTextRepository;
import com.heartape.live.im.jpa.entity.GroupEntity;
import com.heartape.live.im.jpa.entity.GroupTextEntity;
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
public class JdbcGroupTextRepository implements MessageRepository<TextMessage> {

    private final JpaGroupTextRepository groupTextRepository;

    private final JpaGroupBaseRepository groupBaseRepository;

    @Override
    public void save(TextMessage message) {
        GroupEntity groupEntity = entity(message);
        GroupEntity groupEntitySave = groupBaseRepository.save(groupEntity);
        Text text = message.getContent();
        GroupTextEntity groupTextEntity = entity(text);
        groupTextEntity.setMessageId(groupEntitySave.getId());
        groupTextRepository.save(groupTextEntity);
    }

    public GroupEntity entity(BaseMessage<?> message) {
        return new GroupEntity(null, message.getUid(), message.getPurpose(), message.getType(), 0, message.getTimestamp());
    }

    private GroupTextEntity entity(Text text) {
        return new GroupTextEntity(null, null, text.getText());
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
