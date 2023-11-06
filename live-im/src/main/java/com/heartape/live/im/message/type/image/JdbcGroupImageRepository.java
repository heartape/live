package com.heartape.live.im.message.type.image;

import com.heartape.live.im.jpa.JpaGroupBaseRepository;
import com.heartape.live.im.jpa.JpaGroupImageCopyRepository;
import com.heartape.live.im.jpa.JpaGroupImageRepository;
import com.heartape.live.im.jpa.entity.GroupEntity;
import com.heartape.live.im.jpa.entity.GroupImageCopyEntity;
import com.heartape.live.im.jpa.entity.GroupImageEntity;
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
public class JdbcGroupImageRepository implements MessageRepository<ImageMessage> {

    private final JpaGroupImageRepository groupImageRepository;

    private final JpaGroupImageCopyRepository groupImageCopyRepository;

    private final JpaGroupBaseRepository groupBaseRepository;

    @Override
    public void save(ImageMessage message) {
        GroupEntity groupEntity = entity(message);
        GroupEntity groupEntitySave = groupBaseRepository.save(groupEntity);
        Image image = message.getContent();
        GroupImageEntity groupImageEntity = entity(image);
        groupImageEntity.setMessageId(groupEntitySave.getId());
        GroupImageEntity groupImageEntitySave = groupImageRepository.save(groupImageEntity);
        image.getImageCopyList()
                .stream()
                .map(this::entity)
                .peek(groupImageCopyEntity -> groupImageCopyEntity.setImageId(groupImageEntitySave.getId()))
                .forEach(groupImageCopyRepository::save);
    }

    public GroupEntity entity(BaseMessage<?> message) {
        return new GroupEntity(null, message.getUid(), message.getPurpose(), message.getType(), 0, message.getTimestamp());
    }

    private GroupImageEntity entity(Image image) {
        return new GroupImageEntity(null, null, image.getName(), image.getFormat());
    }

    private GroupImageCopyEntity entity(ImageCopy imagecopy) {
        return new GroupImageCopyEntity(null, null, imagecopy.getType(), imagecopy.getSize(), imagecopy.getWidth(), imagecopy.getHeight(), imagecopy.getUrl());
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
