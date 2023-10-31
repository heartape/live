package com.heartape.live.im.manage.group;

import com.heartape.util.id.IdentifierGenerator;

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
    private final Map<String, GroupChat> groupChats;

    public MemoryGroupChatRepository(IdentifierGenerator<Long> identifierGenerator) {
        this.identifierGenerator = identifierGenerator;
        this.groupChats = new ConcurrentHashMap<>();
    }

    @Override
    public void save(GroupChat groupChat) {
        String id = this.identifierGenerator.nextId().toString();
        groupChat.setId(id);
        this.groupChats.put(id, groupChat);
    }

    @Override
    public GroupChat findById(String id) {
        return this.groupChats.get(id);
    }

    @Override
    public List<GroupChat> findByIds(List<String> groupIds) {
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
    public void changeName(String id, String uid, String name) {
        this.groupChats.get(id).setName(name);
    }

    @Override
    public void changeAvatar(String id, String uid, String avatar) {
        this.groupChats.get(id).setAvatar(avatar);
    }

    @Override
    public void changeLevel(String id, String uid, int level) {
        this.groupChats.get(id).setLevel(level);
    }

    @Override
    public void remove(String id, String uid) {
        this.groupChats.remove(id);
    }
}
