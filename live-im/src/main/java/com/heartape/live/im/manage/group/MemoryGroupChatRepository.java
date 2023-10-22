package com.heartape.live.im.manage.group;

import com.heartape.live.im.util.IdentifierGenerator;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 内存实现
 * @see GroupChatRepository
 */
public class MemoryGroupChatRepository implements GroupChatRepository {

    private final IdentifierGenerator<Long> identifierGenerator;

    /**
     * 内存存储
     */
    private final Map<Long, GroupChat> groupChats;

    public MemoryGroupChatRepository(IdentifierGenerator<Long> identifierGenerator) {
        this.identifierGenerator = identifierGenerator;
        this.groupChats = new ConcurrentHashMap<>();
    }

    @Override
    public void save(GroupChat groupChat) {
        Long id = this.identifierGenerator.nextId();
        groupChat.setId(id);
        this.groupChats.put(id, groupChat);
    }

    @Override
    public GroupChat findById(Long id) {
        return this.groupChats.get(id);
    }

    @Override
    public List<GroupChat> findByIds(List<Long> groupIds) {
        return this.groupChats.values()
                .stream()
                .filter(groupChat -> groupIds.contains(groupChat.getId()))
                .toList();
    }

    @Override
    public List<GroupChat> findByName(String name) {
        return this.groupChats.values()
                .stream()
                .filter(groupChat -> groupChat.getName().contains(name))
                .toList();
    }

    @Override
    public List<GroupChat> findByTag(String tag) {
        return this.groupChats.values()
                .stream()
                .filter(groupChat -> groupChat.getTags().contains(tag))
                .toList();
    }

    @Override
    public void changeName(Long id, String uid, String name) {
        this.groupChats.get(id).setName(name);
    }

    @Override
    public void changeAvatar(Long id, String uid, String avatar) {
        this.groupChats.get(id).setAvatar(avatar);
    }

    @Override
    public void changeLevel(Long id, String uid, int level) {
        this.groupChats.get(id).setLevel(level);
    }

    @Override
    public void changeAuthMode(Long id, String uid, int authMode) {
        this.groupChats.get(id).setAuthMode(authMode);
    }

    @Override
    public void remove(Long id, String uid) {
        this.groupChats.remove(id);
    }
}
