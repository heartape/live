package com.heartape.live.im.message.type.file;

import com.heartape.live.im.mapper.SingleBaseMapper;
import com.heartape.live.im.mapper.SingleFileMapper;
import com.heartape.live.im.mapper.entity.SingleEntity;
import com.heartape.live.im.mapper.entity.SingleFileEntity;
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
public class JdbcSingleFileRepository implements MessageRepository<FileMessage> {

    // private final JPAQueryFactory queryFactory;

    private final SingleFileMapper singleFileRepository;

    private final SingleBaseMapper singleBaseRepository;

    @Override
    public void save(FileMessage message) {
        SingleEntity singleEntity = entity(message);
        singleBaseRepository.insert(singleEntity);
        File file = message.getContent();
        SingleFileEntity singleFileEntity = entity(file);
        singleFileEntity.setMessageId(singleEntity.getId());
        singleFileRepository.insert(singleFileEntity);
    }

    public SingleEntity entity(BaseMessage<?> message) {
        return new SingleEntity(null, message.getUid(), message.getPurpose(), message.getType(), false, message.getTimestamp());
    }

    private SingleFileEntity entity(File file) {
        return new SingleFileEntity(null, null, file.getUrl(), file.getFilename(), file.getSize());
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
