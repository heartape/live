package com.heartape.live.im.message;

import com.heartape.util.Page;

import java.time.LocalDateTime;

public interface GroupMessageRepository<T extends Message> extends MessageRepository<T> {

    /**
     * 查询
     * @param id 消息id
     * @param groupId 群聊id
     * @return 消息
     */
    T findById(String id, String groupId);

    /**
     * 根据消息group拉取（全量）
     * @param groupId 消息发送对象
     * @param page 页码
     * @param size 分页宽度
     * @return 消息
     */
    Page<T> findByGroupId(String groupId, int page, int size);

    /**
     * 根据消息发送对象和起始id拉取（增量）
     * @param id 起始消息id
     * @param page 页码
     * @param size 分页宽度
     * @return 消息
     */
    Page<T> findByGroupIdAndStartId(String groupId, String id, int page, int size);

    /**
     * 根据消息发送对象拉取用户发送的消息（全量）（消息漫游）
     * @param groupId 消息发送对象
     * @param start 开始时间
     * @param page 页码
     * @param size 分页宽度
     * @return 消息
     */
    Page<T> findByGroupIdAndStartTime(String groupId, LocalDateTime start, int page, int size);

}
