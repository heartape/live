package com.heartape.live.im.message;

import com.heartape.util.Page;

/**
 * 消息存储，建议采用写扩散，并且分离消息写入数据和读取数据
 * @since 0.0.1
 * @author heartape
 */
public interface MessageRepository<T extends Message> {

    /**
     * 保存
     * @param message 消息
     */
    void save(T message);

    /**
     * 查询
     * @param id 消息id
     * @param uid 用户id
     * @return 消息
     */
    T findById(Long id, String uid);

    /**
     * 根据消息发送对象拉取（全量）
     * @param uid 用户id
     * @param purposeId 消息发送对象
     * @param page 页码
     * @param size 分页宽度
     * @return 消息
     */
    Page<T> findByPurposeId(String uid, String purposeId, int page, int size);

    /**
     * 根据消息发送对象和起始id拉取（增量）
     * @param id 起始消息id
     * @param uid 用户id
     * @param page 页码
     * @param size 分页宽度
     * @return 消息
     */
    Page<T> findByStartId(Long id, String uid, String purposeId, int page, int size);

    /**
     * 根据消息发送对象拉取用户发送的消息（全量）（消息漫游）
     * @param uid 用户id
     * @param purposeId 消息发送对象
     * @param page 页码
     * @param size 分页宽度
     * @return 消息
     */
    Page<T> findRoamingByPurposeId(String uid, String purposeId, int page, int size);

    /**
     * 根据消息发送对象和起始id拉取用户发送的消息（增量）（消息漫游）
     * @param id 起始消息id
     * @param uid 用户id
     * @param page 页码
     * @param size 分页宽度
     * @return 消息
     */
    Page<T> findRoamingByStartId(Long id, String uid, String purposeId, int page, int size);

    /**
     * 已读回执
     * @param id 消息id
     * @param uid 用户id
     */
    void receipt(Long id, String uid);

    /**
     * 撤回
     * @param id 消息id
     * @param uid 用户id
     */
    void recall(Long id, String uid);

    /**
     * 删除
     * @param id 消息id
     * @param uid 用户id
     */
    void remove(Long id, String uid);

}
