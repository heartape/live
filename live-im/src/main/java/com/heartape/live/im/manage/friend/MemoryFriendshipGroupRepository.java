package com.heartape.live.im.manage.friend;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 内存实现
 * @see FriendshipGroupRepository
 */
public class MemoryFriendshipGroupRepository implements FriendshipGroupRepository {

    /**
     * 内存存储
     */
    private final List<FriendshipGroup> friendshipGroups;

    public MemoryFriendshipGroupRepository() {
        this.friendshipGroups = new CopyOnWriteArrayList<>();
    }

    @Override
    public void insert(FriendshipGroup friendshipGroup) {
        this.friendshipGroups.add(friendshipGroup);
    }

    @Override
    public FriendshipGroup selectById(String id) {
        return this.friendshipGroups.stream()
                .filter(friendshipGroup -> friendshipGroup.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<FriendshipGroup> selectList(String uid) {
        return this.friendshipGroups.stream()
                .filter(friendshipGroup -> friendshipGroup.getUid().equals(uid))
                .toList();
    }

    @Override
    public boolean update(String id, String uid, String name) {
        for (FriendshipGroup friendshipGroup : this.friendshipGroups) {
            if (friendshipGroup.getId().equals(id) && friendshipGroup.getUid().equals(uid)) {
                friendshipGroup.setName(name);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(String id, String uid) {
        return this.friendshipGroups.removeIf(friendshipGroup -> friendshipGroup.getId().equals(id) && friendshipGroup.getUid().equals(uid));
    }
}
