package com.heartape.live.im.message;

import com.heartape.util.Page;

import java.time.LocalDateTime;

public interface SingleMessageRepository<T extends Message> extends MessageRepository<T>  {

    /**
     * 查询
     * @param id 消息id
     * @param uid 用户id
     * @return 消息
     */
    T findById(String id, String uid);

    /**
     * 根据消息发送对象拉取（全量）
     * @param uid 用户id
     * @param personId 消息发送对象
     * @param page 页码
     * @param size 分页宽度
     * @return 消息
     */
    Page<T> findByPersonId(String uid, String personId, int page, int size);

    /**
     * 根据消息发送对象和起始id拉取（增量）
     * @param id 起始消息id
     * @param uid 用户id
     * @param page 页码
     * @param size 分页宽度
     * @return 消息
     */
    Page<T> findByStartId(String id, String uid, String personId, int page, int size);

    /**
     * 根据消息发送对象和起始id拉取用户发送的消息（增量）（消息漫游）
     * @param start 起始时间
     * @param uid 用户id
     * @param page 页码
     * @param size 分页宽度
     * @return 消息
     */
    Page<T> findByStartTime(LocalDateTime start, String uid, String personId, int page, int size);

}
