package com.heartape.live.im.manage.friend;

import java.util.List;

/**
 * 表情包存储
 */
public interface FriendshipRepository {

    /**
     * 插入
     * @param uid 用户
     * @param friendId 好友
     * @param groupId 分组
     * @param friendGroupId 好友分组
     */
    void insert(String uid, String friendId, Long groupId, Long friendGroupId);

    /**
     * 根据id查询
     * @param id id
     * @return 好友
     */
    Friendship selectById(Long id);

    /**
     * 查询所有好友
     * @param uid uid
     * @return 好友列表
     */
    List<Friendship> selectList(String uid);

    /**
     * 查询所有好友
     * @param groupId groupId
     * @return 好友列表
     */
    List<Friendship> selectListByGroup(Long groupId);

    /**
     * 是否好友
     * @param uid 用户id
     * @param friendId 好友id
     */
    boolean isFriend(String uid, String friendId);

    /**
     * 更新好友分组
     * @param uid uid
     * @param friendId friendId
     * @param groupId groupId
     */
    void update(String uid, String friendId, Long groupId);

    /**
     * 更改好友关系类型
     * @param uid uid
     * @param friendId friendId
     * @param type 好友关系类型
     */
    void update(String uid, String friendId, Integer type);

    /**
     * 删除
     * @param uid uid
     * @param friendId friendId
     */
    boolean delete(String uid, String friendId);

}
