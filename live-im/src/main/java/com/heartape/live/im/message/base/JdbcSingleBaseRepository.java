package com.heartape.live.im.message.base;

import com.heartape.live.im.jpa.JpaSingleBaseRepository;
import com.heartape.live.im.message.MessageRepository;
import com.heartape.live.im.message.type.file.FileMessage;
import com.heartape.util.Page;
import lombok.AllArgsConstructor;

/**
 * 基于jdbc的实现，读扩散设计
 * @since 0.0.1
 * @author heartape
 * @see AbstractCenterBaseRepository
 */
@AllArgsConstructor
public class JdbcSingleBaseRepository implements MessageRepository<BaseMessage<?>> {

    private final JpaSingleBaseRepository jpaSingleBaseRepository;

    @Override
    public void save(BaseMessage<?> message) {

    }

    @Override
    public FileMessage findById(String id, String uid) {
        return null;
    }

    @Override
    public Page<BaseMessage<?>> findByPurposeId(String uid, String purposeId, int page, int size) {
        return null;
    }

    @Override
    public Page<BaseMessage<?>> findByStartId(String id, String uid, String purposeId, int page, int size) {
        return null;
    }

    @Override
    public Page<BaseMessage<?>> findRoamingByPurposeId(String uid, String purposeId, int page, int size) {
        return null;
    }

    @Override
    public Page<BaseMessage<?>> findRoamingByStartId(String id, String uid, String purposeId, int page, int size) {
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
