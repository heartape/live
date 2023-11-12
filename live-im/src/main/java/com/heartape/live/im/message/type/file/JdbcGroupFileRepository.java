package com.heartape.live.im.message.type.file;

import com.heartape.live.im.mapper.GroupBaseMapper;
import com.heartape.live.im.mapper.GroupFileMapper;
import com.heartape.live.im.mapper.entity.GroupFileEntity;
import com.heartape.live.im.mapper.entity.GroupEntity;
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
public class JdbcGroupFileRepository implements MessageRepository<FileMessage> {

    // private final JPAQueryFactory queryFactory;

    private final GroupFileMapper groupFileRepository;

    private final GroupBaseMapper groupBaseRepository;

    @Override
    public void save(FileMessage message) {
        GroupEntity groupEntity = entity(message);
        groupBaseRepository.insert(groupEntity);
        File file = message.getContent();
        GroupFileEntity groupFileEntity = entity(file);
        groupFileEntity.setMessageId(groupEntity.getId());
        groupFileRepository.insert(groupFileEntity);
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

    }

}
