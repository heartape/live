package com.heartape.live.im.message.type.file;

import com.heartape.live.im.jpa.JpaSingleBaseRepository;
import com.heartape.live.im.jpa.JpaSingleFileRepository;
import com.heartape.live.im.jpa.entity.GroupFileEntity;
import com.heartape.live.im.jpa.entity.QFile;
import com.heartape.live.im.jpa.entity.SingleEntity;
import com.heartape.live.im.message.MessageRepository;
import com.heartape.live.im.message.base.BaseMessage;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;

/**
 * 基于jdbc的实现
 * @since 0.0.1
 * @author heartape
 * @see MessageRepository
 */
@AllArgsConstructor
public class JdbcSingleFileRepository implements MessageRepository<FileMessage> {

    private final JPAQueryFactory queryFactory;

    private final JpaSingleFileRepository singleFileRepository;

    private final JpaSingleBaseRepository  singleBaseRepository;

    @Override
    public void save(FileMessage message) {
        SingleEntity singleEntity = entity(message);
        SingleEntity singleEntitySave = singleBaseRepository.save(singleEntity);
        File file = message.getContent();
        GroupFileEntity groupFileEntity = entity(file);
        groupFileEntity.setMessageId(singleEntitySave.getId());
        singleFileRepository.save(groupFileEntity);
    }

    public SingleEntity entity(BaseMessage<?> message) {
        return new SingleEntity(null, message.getUid(), message.getPurpose(), message.getType(), false, message.getTimestamp());
    }

    private GroupFileEntity entity(File file) {
        return new GroupFileEntity(null, null, file.getUrl(), file.getFilename(), file.getSize());
    }

    @Override
    public void receipt(String id) {

    }

    @Override
    public void recall(String id) {

    }

    @Override
    public void remove(String id) {
        singleBaseRepository.deleteById(id);
        QFile file = QFile.file;
        queryFactory.delete(file)
                .where(file.messageId.eq(id))
                .execute();
    }

}
