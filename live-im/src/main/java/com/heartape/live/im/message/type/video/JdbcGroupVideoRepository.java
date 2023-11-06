package com.heartape.live.im.message.type.video;

import com.heartape.live.im.jpa.JpaGroupBaseRepository;
import com.heartape.live.im.jpa.JpaGroupVideoRepository;
import com.heartape.live.im.jpa.entity.GroupEntity;
import com.heartape.live.im.jpa.entity.GroupVideoEntity;
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

    private final JpaGroupVideoRepository groupVideoRepository;

    private final JpaGroupBaseRepository groupBaseRepository;

    @Override
    public void save(VideoMessage message) {
        GroupEntity groupEntity = entity(message);
        GroupEntity groupEntitySave = groupBaseRepository.save(groupEntity);
        Video video = message.getContent();
        GroupVideoEntity groupVideoEntity = entity(video);
        groupVideoEntity.setMessageId(groupEntitySave.getId());
        groupVideoRepository.save(groupVideoEntity);
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
