package com.heartape.live.im.message.type.text;

import com.heartape.live.im.jpa.JpaSingleBaseRepository;
import com.heartape.live.im.jpa.JpaSingleTextRepository;
import com.heartape.live.im.jpa.entity.SingleEntity;
import com.heartape.live.im.jpa.entity.SingleTextEntity;
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
public class JdbcSingleTextRepository implements MessageRepository<TextMessage> {

    private final JpaSingleTextRepository singleTextRepository;

    private final JpaSingleBaseRepository singleBaseRepository;

    @Override
    public void save(TextMessage message) {
        SingleEntity singleEntity = entity(message);
        SingleEntity singleEntitySave = singleBaseRepository.save(singleEntity);
        Text text = message.getContent();
        SingleTextEntity singleTextEntity = entity(text);
        singleTextEntity.setMessageId(singleEntitySave.getId());
        singleTextRepository.save(singleTextEntity);
    }

    public SingleEntity entity(BaseMessage<?> message) {
        return new SingleEntity(null, message.getUid(), message.getPurpose(), message.getType(), false, message.getTimestamp());
    }

    private SingleTextEntity entity(Text text) {
        return new SingleTextEntity(null, null, text.getText());
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
