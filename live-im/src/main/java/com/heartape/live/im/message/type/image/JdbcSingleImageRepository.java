package com.heartape.live.im.message.type.image;

import com.heartape.live.im.jpa.JpaSingleBaseRepository;
import com.heartape.live.im.jpa.JpaSingleImageCopyRepository;
import com.heartape.live.im.jpa.JpaSingleImageRepository;
import com.heartape.live.im.jpa.entity.SingleEntity;
import com.heartape.live.im.jpa.entity.SingleImageCopyEntity;
import com.heartape.live.im.jpa.entity.SingleImageEntity;
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

    private final JpaSingleImageRepository singleImageRepository;

    private final JpaSingleImageCopyRepository singleImageCopyRepository;

    private final JpaSingleBaseRepository singleBaseRepository;

    @Override
    public void save(ImageMessage message) {
        SingleEntity singleEntity = entity(message);
        SingleEntity singleEntitySave = singleBaseRepository.save(singleEntity);
        Image image = message.getContent();
        SingleImageEntity singleImageEntity = entity(image);
        singleImageEntity.setMessageId(singleEntitySave.getId());
        SingleImageEntity singleImageEntitySave = singleImageRepository.save(singleImageEntity);
        image.getImageCopyList()
                .stream()
                .map(this::entity)
                .peek(singleImageCopyEntity -> singleImageCopyEntity.setImageId(singleImageEntitySave.getId()))
                .forEach(singleImageCopyRepository::save);
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
