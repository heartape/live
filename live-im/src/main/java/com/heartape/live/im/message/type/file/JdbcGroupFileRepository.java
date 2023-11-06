package com.heartape.live.im.message.type.file;

import com.heartape.live.im.jpa.JpaGroupBaseRepository;
import com.heartape.live.im.jpa.JpaGroupFileRepository;
import com.heartape.live.im.jpa.entity.GroupFileEntity;
import com.heartape.live.im.jpa.entity.GroupEntity;
import com.heartape.live.im.jpa.entity.QFile;
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
public class JdbcGroupFileRepository implements MessageRepository<FileMessage> {

    private final JPAQueryFactory queryFactory;

    private final JpaGroupFileRepository groupFileRepository;

    private final JpaGroupBaseRepository groupBaseRepository;

    @Override
    public void save(FileMessage message) {
        GroupEntity groupEntity = entity(message);
        GroupEntity groupEntitySave = groupBaseRepository.save(groupEntity);
        File file = message.getContent();
        GroupFileEntity groupFileEntity = entity(file);
        groupFileEntity.setMessageId(groupEntitySave.getId());
        groupFileRepository.save(groupFileEntity);
    }

    public GroupEntity entity(BaseMessage<?> message) {
        return new GroupEntity(null, message.getUid(), message.getPurpose(), message.getType(), 0, message.getTimestamp());
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
        groupBaseRepository.deleteById(id);
        QFile file = QFile.file;
        queryFactory.delete(file)
                .where(file.messageId.eq(id))
                .execute();
    }

}
