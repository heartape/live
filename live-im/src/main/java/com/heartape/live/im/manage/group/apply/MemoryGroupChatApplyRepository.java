package com.heartape.live.im.manage.group.apply;

import com.heartape.live.im.util.IdentifierGenerator;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 内存实现
 * @see GroupChatApplyRepository
 */
public class MemoryGroupChatApplyRepository implements GroupChatApplyRepository {

    private final IdentifierGenerator<Long> identifierGenerator;

    /**
     * 内存存储
     */
    private final Map<Long, GroupChatApply> groupChatApplyMap;

    // 结果
    private final static Integer SUCCESS = 1;
    private final static Integer INIT = 0;
    private final static Integer FAIL = -1;


    public MemoryGroupChatApplyRepository(IdentifierGenerator<Long> identifierGenerator) {
        this.identifierGenerator = identifierGenerator;
        this.groupChatApplyMap = new ConcurrentHashMap<>();
    }

    @Override
    public void save(GroupChatApply groupChatApply) {
        Long id = this.identifierGenerator.nextId();
        groupChatApply.setId(id);
        groupChatApply.setResult(INIT);
        this.groupChatApplyMap.put(id, groupChatApply);
    }

    @Override
    public GroupChatApply findById(Long id) {
        return this.groupChatApplyMap.get(id);
    }

    @Override
    public List<GroupChatApply> findByUid(String uid) {
        return this.groupChatApplyMap.values()
                .stream()
                .filter(groupChatApply -> Objects.equals(groupChatApply.getUid(), uid))
                .toList();
    }

    @Override
    public List<GroupChatApply> findByGroupId(Long groupId) {
        return this.groupChatApplyMap.values()
                .stream()
                .filter(groupChatApply -> Objects.equals(groupChatApply.getGroupId(), groupId))
                .toList();
    }

    @Override
    public boolean changeApplySuccess(Long applyId) {
        GroupChatApply groupChatApply = this.groupChatApplyMap.get(applyId);
        if (groupChatApply == null || !Objects.equals(groupChatApply.getResult(), INIT)) {
            return false;
        }
        groupChatApply.setResult(SUCCESS);
        return true;
    }

    @Override
    public boolean changeApplyFail(Long applyId) {
        GroupChatApply groupChatApply = this.groupChatApplyMap.get(applyId);
        if (groupChatApply == null || !Objects.equals(groupChatApply.getResult(), INIT)) {
            return false;
        }
        groupChatApply.setResult(FAIL);
        return true;
    }
}
