package com.heartape.live.im.message.type.video;

import com.heartape.live.im.jpa.JpaSingleBaseRepository;
import com.heartape.live.im.jpa.JpaSingleVideoRepository;
import com.heartape.live.im.jpa.entity.SingleEntity;
import com.heartape.live.im.jpa.entity.SingleVideoEntity;
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
public class JdbcSingleVideoRepository implements MessageRepository<VideoMessage> {

    private final JpaSingleVideoRepository singleVideoRepository;

    private final JpaSingleBaseRepository singleBaseRepository;

    @Override
    public void save(VideoMessage message) {
        SingleEntity singleEntity = entity(message);
        SingleEntity singleEntitySave = singleBaseRepository.save(singleEntity);
        Video video = message.getContent();
        SingleVideoEntity singleVideoEntity = entity(video);
        singleVideoEntity.setMessageId(singleEntitySave.getId());
        singleVideoRepository.save(singleVideoEntity);
    }

    public SingleEntity entity(BaseMessage<?> message) {
        return new SingleEntity(null, message.getUid(), message.getPurpose(), message.getType(), false, message.getTimestamp());
    }

    private SingleVideoEntity entity(Video video) {
        return new SingleVideoEntity(null, null, video.getUrl(), video.getFormat(), video.getSize(), video.getSecond(), video.getThumbId(), video.getThumbFormat(), video.getThumbUrl(), video.getThumbWidth(), video.getThumbHeight());
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
