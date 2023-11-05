package com.heartape.live.im.message.type.location;

import com.heartape.live.im.jpa.JpaGroupBaseRepository;
import com.heartape.live.im.jpa.JpaSingleBaseRepository;
import com.heartape.live.im.jpa.JpaLocationRepository;
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
public class JdbcLocationRepository implements MessageRepository<LocationMessage> {

    private final JpaLocationRepository jpaLocationRepository;

    private final JpaSingleBaseRepository jpaSingleBaseRepository;

    private final JpaGroupBaseRepository jpaGroupBaseRepository;

    @Override
    public void save(LocationMessage message) {

    }

    @Override
    public LocationMessage findById(String id, String uid) {
        return null;
    }

    @Override
    public Page<LocationMessage> findByPurposeId(String uid, String purposeId, int page, int size) {
        return null;
    }

    @Override
    public Page<LocationMessage> findByStartId(String id, String uid, String purposeId, int page, int size) {
        return null;
    }

    @Override
    public Page<LocationMessage> findRoamingByPurposeId(String uid, String purposeId, int page, int size) {
        return null;
    }

    @Override
    public Page<LocationMessage> findRoamingByStartId(String id, String uid, String purposeId, int page, int size) {
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
