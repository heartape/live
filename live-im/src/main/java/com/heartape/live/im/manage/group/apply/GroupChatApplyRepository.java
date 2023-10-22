package com.heartape.live.im.manage.group.apply;

import java.util.List;

/**
 * 加入群聊申请存储库
 */
public interface GroupChatApplyRepository {

    /**
     * 保存
     */
    void save(GroupChatApply groupChatApply);

    /**
     * 根据id查询
     */
    GroupChatApply findById(Long id);

    /**
     * 根据uid查询
     */
    List<GroupChatApply> findByUid(String uid);

    /**
     * 根据GroupId查询
     */
    List<GroupChatApply> findByGroupId(Long groupId);

    /**
     * 修改申请结果
     */
    boolean changeApplySuccess(Long applyId);

    /**
     * 修改申请结果失败
     */
    boolean changeApplyFail(Long applyId);

}
