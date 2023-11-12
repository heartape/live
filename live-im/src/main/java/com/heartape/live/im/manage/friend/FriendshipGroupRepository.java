package com.heartape.live.im.manage.friend;

import java.util.List;

/**
 * 好友分组repository
 */
public interface FriendshipGroupRepository {

    /**
     * 插入
     * @param friendshipGroup 好友分组
     */
    void insert(FriendshipGroup friendshipGroup);

    /**
     * 根据id查询
     * @param id id
     * @return 好友分组
     */
    FriendshipGroup selectById(String id);

    /**
     * 查询所有好友分组
     * @param uid uid
     * @return 好友分组列表
     */
    List<FriendshipGroup> selectList(String uid);

    boolean update(String id, String uid, String name);

    /**
     * 删除好友分组
     * @param id id
     */
    boolean delete(String id, String uid);

}
