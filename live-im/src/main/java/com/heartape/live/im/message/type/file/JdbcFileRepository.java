package com.heartape.live.im.message.type.file;

import com.heartape.live.im.jpa.JpaGroupBaseRepository;
import com.heartape.live.im.jpa.JpaSingleBaseRepository;
import com.heartape.live.im.jpa.JpaFileRepository;
import com.heartape.live.im.message.MessageRepository;
import com.heartape.live.im.message.base.AbstractCenterBaseRepository;
import com.heartape.util.Page;
import lombok.AllArgsConstructor;

/**
 * 基于jdbc的实现
 * @since 0.0.1
 * @author heartape
 * @see AbstractCenterBaseRepository
 */
@AllArgsConstructor
public class JdbcFileRepository implements MessageRepository<FileMessage> {

    private final JpaFileRepository jpaFileRepository;

    private final JpaSingleBaseRepository jpaSingleBaseRepository;

    private final JpaGroupBaseRepository jpaGroupBaseRepository;

    @Override
    public void save(FileMessage message) {

    }

    @Override
    public FileMessage findById(String id, String uid) {
        return null;
    }

    @Override
    public Page<FileMessage> findByPurposeId(String uid, String purposeId, int page, int size) {
        return null;
    }

    @Override
    public Page<FileMessage> findByStartId(String id, String uid, String purposeId, int page, int size) {
        return null;
    }

    @Override
    public Page<FileMessage> findRoamingByPurposeId(String uid, String purposeId, int page, int size) {
        return null;
    }

    @Override
    public Page<FileMessage> findRoamingByStartId(String id, String uid, String purposeId, int page, int size) {
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
