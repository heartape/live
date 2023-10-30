package com.heartape.live.im.message.center;

import com.heartape.live.im.gateway.PurposeType;
import com.heartape.util.Page;
import com.heartape.live.im.message.base.BaseMessage;

/**
 * 基础消息仓库基类
 * @see BaseMessage
 * @since 0.0.1
 * @author heartape
 */
public interface CenterMessageRepository {

    /**
     * @return purposeType
     * @see PurposeType
     */
    String purposeType();

    /**
     * 保存
     * @param message 消息
     */
    void save(BaseMessage message);

    /**
     * 查询
     * @param id 消息id
     * @param uid 用户id
     * @return 消息
     */
    BaseMessage findById(String id, String uid);

    /**
     * 根据消息发送对象拉取（全量）
     * @param uid 用户id
     * @param purposeId 消息发送对象
     * @return 消息
     */
    Page<BaseMessage> findByPurposeId(String uid, String purposeId, int page, int size);

    /**
     * 根据消息发送对象和起始id拉取（增量）
     * @param id 起始消息id
     * @param uid 用户id
     * @param page 页码
     * @param size 分页宽度
     * @return 消息
     */
    Page<BaseMessage> findByStartId(String id, String uid, String purposeId, int page, int size);

    /**
     * 根据消息发送对象拉取用户发送的消息（全量）（消息漫游）
     * @param uid 用户id
     * @param purposeId 消息发送对象
     * @return 消息
     */
    Page<BaseMessage> findRoamingByPurposeId(String uid, String purposeId, int page, int size);

    /**
     * 根据消息发送对象和起始id拉取用户发送的消息（增量）（消息漫游）
     * @param id 起始消息id
     * @param uid 用户id
     * @param page 页码
     * @param size 分页宽度
     * @return 消息
     */
    Page<BaseMessage> findRoamingByStartId(String id, String uid, String purposeId, int page, int size);

    /**
     * 根据消息发送对象拉取某一类型的消息（全量）
     * @param uid 用户id
     * @param page 页码
     * @param size 分页宽度
     * @return 消息
     */
    Page<BaseMessage> findByPurposeIdAndType(String uid, String purposeId, String messageType, int page, int size);

    /**
     * 根据消息发送对象和起始id拉取某一类型的消息（增量）
     * @param id 起始消息id
     * @param uid 用户id
     * @param page 页码
     * @param size 分页宽度
     * @return 消息
     */
    Page<BaseMessage> findByStartIdAndType(String id, String uid, String purposeId, String messageType, int page, int size);

    /**
     * 根据消息发送对象拉取用户发送的消息（全量）（消息漫游）
     * @param uid 用户id
     * @param purposeId 消息发送对象
     * @return 消息
     */
    Page<BaseMessage> findRoamingByPurposeIdAndType(String uid, String purposeId, String messageType, int page, int size);

    /**
     * 根据消息发送对象和起始id拉取用户发送的消息（增量）（消息漫游）
     * @param id 起始消息id
     * @param uid 用户id
     * @param page 页码
     * @param size 分页宽度
     * @return 消息
     */
    Page<BaseMessage> findRoamingByStartIdAndType(String id, String uid, String purposeId, String messageType, int page, int size);

    /**
     * 已读回执
     * @param id 消息id
     * @param uid 用户id
     */
    void receipt(String id, String uid);

    /**
     * 撤回，超过一定时间后无法撤回
     * @param id 消息id
     * @param uid 用户id
     */
    void recall(String id, String uid);

    /**
     * 删除
     * @param id 消息id
     * @param uid 用户id
     */
    void remove(String id, String uid);

}
