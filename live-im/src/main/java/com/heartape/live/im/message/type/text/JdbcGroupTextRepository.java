package com.heartape.live.im.message.type.text;

import com.heartape.live.im.mapper.GroupBaseMapper;
import com.heartape.live.im.mapper.GroupTextMapper;
import com.heartape.live.im.mapper.entity.GroupEntity;
import com.heartape.live.im.mapper.entity.GroupTextEntity;
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

    private final GroupTextMapper groupTextRepository;

    private final GroupBaseMapper groupBaseRepository;

    @Override
    public void save(TextMessage message) {
        GroupEntity groupEntity = entity(message);
        groupBaseRepository.insert(groupEntity);
        Text text = message.getContent();
        GroupTextEntity groupTextEntity = entity(text);
        groupTextEntity.setMessageId(groupEntity.getId());
        groupTextRepository.insert(groupTextEntity);
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
