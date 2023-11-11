package com.heartape.live.im.message.type.video;

import com.heartape.live.im.mapper.GroupBaseMapper;
import com.heartape.live.im.mapper.GroupVideoMapper;
import com.heartape.live.im.mapper.entity.GroupEntity;
import com.heartape.live.im.mapper.entity.GroupVideoEntity;
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
public class JdbcGroupVideoRepository implements MessageRepository<VideoMessage> {

    private final GroupVideoMapper groupVideoRepository;

    private final GroupBaseMapper groupBaseRepository;

    @Override
    public void save(VideoMessage message) {
        GroupEntity groupEntity = entity(message);
        groupBaseRepository.insert(groupEntity);
        Video video = message.getContent();
        GroupVideoEntity groupVideoEntity = entity(video);
        groupVideoEntity.setMessageId(groupEntity.getId());
        groupVideoRepository.insert(groupVideoEntity);
    }

    public GroupEntity entity(BaseMessage<?> message) {
        return new GroupEntity(null, message.getUid(), message.getPurpose(), message.getType(), 0, message.getTimestamp());
    }

    private GroupVideoEntity entity(Video video) {
        return new GroupVideoEntity(null, null, video.getUrl(), video.getFormat(), video.getSize(), video.getSecond(), video.getThumbId(), video.getThumbFormat(), video.getThumbUrl(), video.getThumbWidth(), video.getThumbHeight());
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
