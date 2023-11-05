package com.heartape.live.im.message.type.text;

import com.heartape.live.im.jpa.JpaGroupBaseRepository;
import com.heartape.live.im.jpa.JpaSingleBaseRepository;
import com.heartape.live.im.jpa.JpaTextRepository;
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
public class JdbcTextRepository implements MessageRepository<TextMessage> {

    private final JpaTextRepository jpaTextRepository;

    private final JpaSingleBaseRepository jpaSingleBaseRepository;

    private final JpaGroupBaseRepository jpaGroupBaseRepository;

    @Override
    public void save(TextMessage message) {

    }

    @Override
    public TextMessage findById(String id, String uid) {
        return null;
    }

    @Override
    public Page<TextMessage> findByPurposeId(String uid, String purposeId, int page, int size) {
        return null;
    }

    @Override
    public Page<TextMessage> findByStartId(String id, String uid, String purposeId, int page, int size) {
        return null;
    }

    @Override
    public Page<TextMessage> findRoamingByPurposeId(String uid, String purposeId, int page, int size) {
        return null;
    }

    @Override
    public Page<TextMessage> findRoamingByStartId(String id, String uid, String purposeId, int page, int size) {
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
