package com.heartape.live.im.message.type.video;

import com.heartape.live.im.jpa.JpaGroupBaseRepository;
import com.heartape.live.im.jpa.JpaSingleBaseRepository;
import com.heartape.live.im.jpa.JpaVideoRepository;
import com.heartape.live.im.message.MessageRepository;
import com.heartape.live.im.message.base.AbstractCenterBaseRepository;
import com.heartape.util.Page;
import lombok.AllArgsConstructor;

/**
 * 基于jdbc的实现
 * @since 0.0.1
 * @author heartape
 * @see MessageRepository
 * @see AbstractCenterBaseRepository
 */
@AllArgsConstructor
public class JdbcVideoRepository implements MessageRepository<VideoMessage> {

    private final JpaVideoRepository jpaVideoRepository;

    private final JpaSingleBaseRepository jpaSingleBaseRepository;

    private final JpaGroupBaseRepository jpaGroupBaseRepository;

    @Override
    public void save(VideoMessage message) {

    }

    @Override
    public VideoMessage findById(String id, String uid) {
        return null;
    }

    @Override
    public Page<VideoMessage> findByPurposeId(String uid, String purposeId, int page, int size) {
        return null;
    }

    @Override
    public Page<VideoMessage> findByStartId(String id, String uid, String purposeId, int page, int size) {
        return null;
    }

    @Override
    public Page<VideoMessage> findRoamingByPurposeId(String uid, String purposeId, int page, int size) {
        return null;
    }

    @Override
    public Page<VideoMessage> findRoamingByStartId(String id, String uid, String purposeId, int page, int size) {
        return null;
    }

    @Override
    public void receipt(String id, String uid) {

    }

    @Override
    public void recall(String id, String uid) {

    }

    @Override
    public void remove(String id, String uid) {

    }
}
