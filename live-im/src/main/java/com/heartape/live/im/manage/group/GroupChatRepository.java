package com.heartape.live.im.manage.group;

import java.util.List;

/**
 * 群聊存储库
 */
public interface GroupChatRepository {

    /**
     * 插入
     */
    void save(GroupChat groupChat);

    /**
     * 查询
     */
    GroupChat findById(String id);

    /**
     * 查询
     */
    List<GroupChat> findByIds(List<String> groupIds);

    /**
     * 根据name查询
     */
    List<GroupChat> findByName(String name);

    /**
     * 根据tag查询
     */
    List<GroupChat> findByTag(String tag);

    /**
     * 更新群聊名称
     */
    void changeName(String id, String uid, String name);

    /**
     * 更新群聊头像
     */
    void changeAvatar(String id, String uid, String avatar);

    /**
     * 更新群聊级别
     */
    void changeLevel(String id, String uid, int level);

    /**
     * 更新群聊认证方式
     */
    void changeAuthMode(String id, String uid, int authMode);

    /**
     * 删除
     */
    void remove(String id, String uid);

}
