package com.heartape.live.im.message.type.image;

import com.heartape.live.im.mapper.SingleBaseMapper;
import com.heartape.live.im.mapper.SingleImageCopyMapper;
import com.heartape.live.im.mapper.SingleImageMapper;
import com.heartape.live.im.mapper.entity.SingleEntity;
import com.heartape.live.im.mapper.entity.SingleImageCopyEntity;
import com.heartape.live.im.mapper.entity.SingleImageEntity;
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
public class JdbcSingleImageRepository implements MessageRepository<ImageMessage> {

    private final SingleImageMapper singleImageRepository;

    private final SingleImageCopyMapper singleImageCopyRepository;

    private final SingleBaseMapper singleBaseRepository;

    @Override
    public void save(ImageMessage message) {
        SingleEntity singleEntity = entity(message);
        singleBaseRepository.insert(singleEntity);
        Image image = message.getContent();
        SingleImageEntity singleImageEntity = entity(image);
        singleImageEntity.setMessageId(singleEntity.getId());
        singleImageRepository.insert(singleImageEntity);
        image.getImageCopyList()
                .stream()
                .map(this::entity)
                .peek(singleImageCopyEntity -> singleImageCopyEntity.setImageId(singleImageEntity.getId()))
                .forEach(singleImageCopyRepository::insert);
    }

    public SingleEntity entity(BaseMessage<?> message) {
        return new SingleEntity(null, message.getUid(), message.getPurpose(), message.getType(), false, message.getTimestamp());
    }

    private SingleImageEntity entity(Image image) {
        return new SingleImageEntity(null, null, image.getName(), image.getFormat());
    }

    private SingleImageCopyEntity entity(ImageCopy imagecopy) {
        return new SingleImageCopyEntity(null, null, imagecopy.getType(), imagecopy.getSize(), imagecopy.getWidth(), imagecopy.getHeight(), imagecopy.getUrl());
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
