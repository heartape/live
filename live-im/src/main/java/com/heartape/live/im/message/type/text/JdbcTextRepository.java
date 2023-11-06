package com.heartape.live.im.message.type.text;

import com.heartape.live.im.jpa.JpaGroupBaseRepository;
import com.heartape.live.im.jpa.JpaSingleBaseRepository;
import com.heartape.live.im.jpa.JpaTextRepository;
import com.heartape.live.im.message.MessageRepository;
import lombok.AllArgsConstructor;

/**
 * 基于jdbc的实现
 * @since 0.0.1
 * @author heartape
 * @see MessageRepository
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
    public void receipt(String id) {

    }

    @Override
    public void recall(String id) {

    }

    @Override
    public void remove(String id) {

    }


}
