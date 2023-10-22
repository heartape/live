package com.heartape.live.im.manage.group;

import com.heartape.live.im.manage.group.apply.GroupChatApply;
import com.heartape.live.im.manage.group.info.GroupChatSimpleInfo;
import com.heartape.live.im.manage.group.info.GroupChatMemberInfo;

import java.util.List;

/**
 * 群聊服务
 */
public interface GroupChatService {

    /**
     * 创建群聊
     */
    void createGroupChat(String uid, String name, String avatar, Integer level, Integer authMode, List<String> tag);

    /**
     * 是否成员
     * @param uid 用户id
     * @param groupId 群聊id
     * @return 是否成员
     */
    boolean isMember(String uid, Long groupId);

    /**
     * 获取群聊列表
     * @param uid 用户id
     * @return 群聊列表
     */
    List<GroupChatSimpleInfo> getGroupChatList(String uid);

    /**
     * 获取群聊成员列表
     */
    List<GroupChatMemberInfo> getGroupChatMemberList(Long groupChatId);

    /**
     * 根据名称搜索群聊
     */
    List<GroupChat> searchGroupChatByName(String name);

    /**
     * 根据标签搜索群聊
     */
    List<GroupChat> searchGroupChatByTag(String tag);

    /**
     * 查看主页详情
     */
    GroupChat getGroupChatMainInfo(Long groupChatId);

    /**
     * 申请加入群聊
     */
    void applyJoinGroupChat(String uid, Long groupChatId);

    /**
     * 查看群聊加入申请
     */
    List<GroupChatApply> getGroupChatApplyList(String uid);

    /**
     * 同意群聊加入申请
     */
    void agreeJoinGroupChat(String uid, Long groupChatApplyId);

    /**
     * 拒绝群聊加入申请
     */
    void rejectJoinGroupChat(String uid, Long groupChatApplyId);

}
