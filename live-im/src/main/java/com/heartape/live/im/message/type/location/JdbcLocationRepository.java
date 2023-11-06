package com.heartape.live.im.message.type.location;

import com.heartape.live.im.jpa.JpaGroupBaseRepository;
import com.heartape.live.im.jpa.JpaSingleBaseRepository;
import com.heartape.live.im.jpa.JpaLocationRepository;
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
public class JdbcLocationRepository implements MessageRepository<LocationMessage> {

    private final JpaLocationRepository jpaLocationRepository;

    private final JpaSingleBaseRepository jpaSingleBaseRepository;

    private final JpaGroupBaseRepository jpaGroupBaseRepository;

    @Override
    public void save(LocationMessage message) {

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
