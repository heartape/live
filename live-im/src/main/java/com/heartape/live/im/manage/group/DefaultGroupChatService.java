package com.heartape.live.im.manage.group;

import com.heartape.live.im.exception.PermissionDeniedException;
import com.heartape.live.im.manage.group.apply.GroupChatApply;
import com.heartape.live.im.manage.group.apply.GroupChatApplyRepository;
import com.heartape.live.im.manage.group.info.GroupChatMemberInfo;
import com.heartape.live.im.manage.group.info.GroupChatSimpleInfo;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * 默认实现
 * @see GroupChatService
 */
@AllArgsConstructor
public class DefaultGroupChatService implements GroupChatService {

    private final GroupChatRepository groupChatRepository;

    private final GroupChatMemberRepository groupChatMemberRepository;

    private final GroupChatApplyRepository groupChatApplyRepository;

    @Override
    public void createGroupChat(String uid, String name, String avatar, Integer level, Integer authMode, List<String> tag) {
        GroupChat groupChat = new GroupChat(null, name, avatar, level, authMode, tag, LocalDateTime.now());
        this.groupChatRepository.save(groupChat);
        GroupChatMember groupChatMember = new GroupChatMember(null, uid, groupChat.getId(), null, null);
        this.groupChatMemberRepository.saveMaster(groupChatMember);
    }

    @Override
    public boolean isMember(String uid, Long groupChatId) {
        return this.groupChatMemberRepository.findByUidAndGroupId(uid, groupChatId) != null;
    }

    @Override
    public List<GroupChatSimpleInfo> getGroupChatList(String uid) {
        List<GroupChatMember> groupChatMembers = this.groupChatMemberRepository.findByUid(uid);
        List<Long> groupIds = groupChatMembers
                .stream()
                .map(GroupChatMember::getGroupId)
                .toList();
        return this.groupChatRepository.findByIds(groupIds)
                .stream()
                .map(groupChat -> new GroupChatSimpleInfo(groupChat.getId(), groupChat.getName(), groupChat.getAvatar()))
                .toList();
    }

    @Override
    public List<GroupChatMemberInfo> getGroupChatMemberList(Long groupChatId) {
        return this.groupChatMemberRepository.findByGroupId(groupChatId)
                .stream()
                .map(groupChatMember -> new GroupChatMemberInfo(groupChatMember.getUid(), groupChatMember.getRole()))
                .toList();
    }

    @Override
    public List<GroupChat> searchGroupChatByName(String name) {
        return this.groupChatRepository.findByName(name);
    }

    @Override
    public List<GroupChat> searchGroupChatByTag(String tag) {
        return this.groupChatRepository.findByTag(tag);
    }

    @Override
    public GroupChat getGroupChatMainInfo(Long groupChatId) {
        return this.groupChatRepository.findById(groupChatId);
    }

    @Override
    public void applyJoinGroupChat(String uid, Long groupChatId) {
        GroupChatApply groupChatApply = new GroupChatApply(null, uid, groupChatId, LocalDateTime.now(), null);
        this.groupChatApplyRepository.save(groupChatApply);
    }

    @Override
    public List<GroupChatApply> getGroupChatApplyList(String uid) {
        List<GroupChatMember> groupChatMemberList = this.groupChatMemberRepository.findByAdmin(uid);
        return groupChatMemberList
                .stream()
                .map(GroupChatMember::getGroupId)
                .map(this.groupChatApplyRepository::findByGroupId)
                .flatMap(List::stream)
                .toList();
    }

    @Override
    public void agreeJoinGroupChat(String uid, Long groupChatApplyId) {
        GroupChatApply groupChatApply = this.groupChatApplyRepository.findById(groupChatApplyId);
        GroupChatMember groupChatMember = new GroupChatMember(null, groupChatApply.getUid(), groupChatApply.getGroupId(), null, null);
        this.groupChatMemberRepository.save(groupChatMember);
        this.groupChatApplyRepository.changeApplySuccess(groupChatApplyId);
    }

    @Override
    public void rejectJoinGroupChat(String uid, Long groupChatApplyId) {
        GroupChatApply groupChatApply = this.groupChatApplyRepository.findById(groupChatApplyId);
        if (!Objects.equals(groupChatApply.getUid(), uid)){
            throw new PermissionDeniedException();
        }
        this.groupChatApplyRepository.changeApplyFail(groupChatApplyId);
    }
}
