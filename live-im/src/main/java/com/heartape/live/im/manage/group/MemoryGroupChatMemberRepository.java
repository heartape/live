package com.heartape.live.im.manage.group;

import com.heartape.util.id.IdentifierGenerator;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 内存实现
 * @see GroupChatMemberRepository
 */
public class MemoryGroupChatMemberRepository implements GroupChatMemberRepository {

    private final IdentifierGenerator<Long> identifierGenerator;

    /**
     * 内存存储
     */
    private final Map<Long, GroupChatMember> groupChatMemberMap;

    // 角色
    private final static Integer MASTER = 1;
    private final static Integer ADMIN = 2;
    private final static Integer MEMBER = 3;

    // 类型
    private final static Integer NORMAL = 1;

    public MemoryGroupChatMemberRepository(IdentifierGenerator<Long> identifierGenerator) {
        this.identifierGenerator = identifierGenerator;
        this.groupChatMemberMap = new ConcurrentHashMap<>();
    }

    @Override
    public void save(GroupChatMember groupChatMember) {
        Long id = this.identifierGenerator.nextId();
        groupChatMember.setId(id);
        groupChatMember.setRole(MEMBER);
        groupChatMember.setType(NORMAL);
        this.groupChatMemberMap.put(id, groupChatMember);
    }

    @Override
    public void saveMaster(GroupChatMember groupChatMember) {
        Long id = this.identifierGenerator.nextId();
        groupChatMember.setId(id);
        groupChatMember.setRole(MASTER);
        groupChatMember.setType(NORMAL);
        this.groupChatMemberMap.put(id, groupChatMember);
    }

    @Override
    public GroupChatMember findById(Long id) {
        return this.groupChatMemberMap.get(id);
    }

    @Override
    public List<GroupChatMember> findByUid(String uid) {
        return this.groupChatMemberMap.values()
                .stream()
                .filter(groupChatMember -> groupChatMember.getUid().equals(uid))
                .toList();
    }

    @Override
    public List<GroupChatMember> findByAdmin(String uid) {
        return this.groupChatMemberMap.values()
                .stream()
                .filter(groupChatMember -> groupChatMember.getUid().equals(uid) && (groupChatMember.getRole().equals(ADMIN) || groupChatMember.getRole().equals(MASTER)))
                .toList();
    }

    @Override
    public List<GroupChatMember> findByGroupId(Long groupId) {
        return this.groupChatMemberMap.values()
                .stream()
                .filter(groupChatMember -> groupChatMember.getGroupId().equals(groupId))
                .toList();
    }

    @Override
    public GroupChatMember findByUidAndGroupId(String uid, Long groupId) {
        return this.groupChatMemberMap.values()
                .stream()
                .filter(groupChatMember -> groupChatMember.getUid().equals(uid) && groupChatMember.getGroupId().equals(groupId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void changeRole(Long id, Integer role) {
        GroupChatMember groupChatMember = this.groupChatMemberMap.get(id);
        groupChatMember.setRole(role);
    }

    @Override
    public void changeType(Long id, Integer type) {
        GroupChatMember groupChatMember = this.groupChatMemberMap.get(id);
        groupChatMember.setType(type);
    }

    @Override
    public void remove(Long id) {
        this.groupChatMemberMap.remove(id);
    }
}
