package com.heartape.live.im.manage.group;

import java.util.List;

/**
 * 群聊成员存储库
 */
public interface GroupChatMemberRepository {

    /**
     * 插入
     */
    void save(GroupChatMember groupChatMember);

    /**
     * 插入
     */
    void saveMaster(GroupChatMember groupChatMember);

    /**
     * 根据id查询
     */
    GroupChatMember findById(String id);

    /**
     * 根据uid查询
     */
    List<GroupChatMember> findByUid(String uid);

    /**
     * 根据管理员uid查询
     */
    List<GroupChatMember> findByAdmin(String uid);

    /**
     * 根据groupId查询
     */
    List<GroupChatMember> findByGroupId(String groupId);

    /**
     * 根据uid 和 groupId查询
     */
    GroupChatMember findByUidAndGroupId(String uid, String groupId);

    /**
     * 更新角色
     */
    void changeRole(String id, Integer role);

    /**
     * 更新类型
     */
    void changeType(String id, Integer type);

    /**
     * 删除
     */
    void remove(String id);

}
