package com.heartape.live.im.message.type.greeting;

import com.heartape.live.im.jpa.JpaGroupBaseRepository;
import com.heartape.live.im.jpa.JpaSingleBaseRepository;
import com.heartape.live.im.jpa.JpaGreetingRepository;
import com.heartape.live.im.message.MessageRepository;
import com.heartape.util.Page;
import lombok.AllArgsConstructor;

/**
 * 基于jdbc的实现
 * @since 0.0.1
 * @author heartape
 * @see MessageRepository
 */
@AllArgsConstructor
public class JdbcGreetingRepository implements MessageRepository<GreetingMessage> {

    private final JpaGreetingRepository jpaGreetingRepository;

    private final JpaSingleBaseRepository jpaSingleBaseRepository;

    private final JpaGroupBaseRepository jpaGroupBaseRepository;

    @Override
    public void save(GreetingMessage message) {

    }

    @Override
    public GreetingMessage findById(String id, String uid) {
        return null;
    }

    @Override
    public Page<GreetingMessage> findByPurposeId(String uid, String purposeId, int page, int size) {
        return null;
    }

    @Override
    public Page<GreetingMessage> findByStartId(String id, String uid, String purposeId, int page, int size) {
        return null;
    }

    @Override
    public Page<GreetingMessage> findRoamingByPurposeId(String uid, String purposeId, int page, int size) {
        return null;
    }

    @Override
    public Page<GreetingMessage> findRoamingByStartId(String id, String uid, String purposeId, int page, int size) {
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
