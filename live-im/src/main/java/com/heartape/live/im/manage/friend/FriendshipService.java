package com.heartape.live.im.manage.friend;

/**
 * 好友服务
 */
public interface FriendshipService {

    /**
     * 添加好友分组
     * @param uid 用户id
     * @param name 分组名称
     */
    void addGroup(String uid, String name);

    /**
     * 删除好友分组
     * @param uid 用户id
     * @param groupId 分组id
     */
    void removeGroup(String uid, Long groupId);

    /**
     * 重命名分组
     * @param groupId 分组id
     * @param uid 用户id
     * @param name 分组名称
     */
    void renameGroup(Long groupId, String uid, String name);

    /**
     * 添加好友
     * @param uid 用户id
     * @param friendId 好友id
     * @param groupId 分组id
     */
    void addFriend(String uid, String friendId, Long groupId, Long friendGroupId);

    /**
     * 是否好友
     * @param uid 用户id
     * @param friendId 好友id
     */
    boolean isFriend(String uid, String friendId);

    /**
     * 更新好友所在分组
     * @param uid 用户id
     * @param friendId 好友id
     * @param groupId 分组id
     */
    void updateFriendGroup(String uid, String friendId, Long groupId);

    /**
     * 删除好友
     * @param uid 用户id
     * @param friendId 好友id
     */
    void removeFriend(String uid, String friendId);

    /**
     * 将好友添加到黑名单
     * @param uid 用户id
     * @param friendId 好友id
     */
    void addBlackList(String uid, String friendId);

    /**
     * 将好友从黑名单移除
     * @param uid 用户id
     * @param friendId 好友id
     */
    void removeBlackList(String uid, String friendId);

}
