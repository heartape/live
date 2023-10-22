package com.heartape.live.im.manage.friend;

import com.heartape.live.im.util.IdentifierGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 内存实现
 * @see FriendshipRepository
 */
public class MemoryFriendshipRepository implements FriendshipRepository {

    private final IdentifierGenerator<Long> identifierGenerator;

    /**
     * 内存存储
     */
    private final Map<String, List<Friendship>> map;

    public MemoryFriendshipRepository(IdentifierGenerator<Long> identifierGenerator) {
        this.identifierGenerator = identifierGenerator;
        this.map = new ConcurrentHashMap<>();
    }

    @Override
    public void insert(String uid, String friendId, Long groupId, Long friendGroupId) {
        List<Friendship> list1 = map.computeIfAbsent(uid, k -> new ArrayList<>());
        List<Friendship> list2 = map.computeIfAbsent(friendId, k -> new ArrayList<>());
        Friendship friendship1 = new Friendship(identifierGenerator.nextId(), groupId, uid, friendId, 1);
        Friendship friendship2 = new Friendship(identifierGenerator.nextId(), friendGroupId, friendId, uid, 1);
        list1.add(friendship1);
        list2.add(friendship2);
    }

    @Override
    public Friendship selectById(Long id) {
        for (List<Friendship> friendshipList : this.map.values()) {
            for (Friendship friendship : friendshipList) {
                if (friendship.getId().equals(id)) {
                    return friendship;
                }
            }
        }
        return null;
    }

    @Override
    public List<Friendship> selectList(String uid) {
        return this.map.get(uid);
    }

    @Override
    public List<Friendship> selectListByGroup(Long groupId) {
        List<Friendship> result = new ArrayList<>();
        for (List<Friendship> friendshipList : this.map.values()) {
            for (Friendship friendship : friendshipList) {
                if (Objects.equals(friendship.getGroupId(), groupId)) {
                    result.add(friendship);
                }
            }
        }
        return result;
    }

    @Override
    public boolean isFriend(String uid, String friendId) {
        List<Friendship> list = map.get(uid);
        if (list == null) {
            return false;
        }
        for (Friendship friendship : list) {
            if (Objects.equals(friendship.getFriendId(), friendId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void update(String uid, String friendId, Long groupId) {
        List<Friendship> list = map.get(uid);
        for (Friendship friendship : list) {
            if (Objects.equals(friendship.getFriendId(), friendId)) {
                friendship.setGroupId(groupId);
            }
        }
    }

    @Override
    public void update(String uid, String friendId, Integer type) {
        List<Friendship> list = map.get(uid);
        for (Friendship friendship : list) {
            if (Objects.equals(friendship.getFriendId(), friendId)) {
                friendship.setType(type);
            }
        }
    }

    @Override
    public boolean delete(String uid, String friendId) {
        List<Friendship> list1 = map.get(uid);
        List<Friendship> list2 = map.get(friendId);
        boolean result1 = false;
        boolean result2 = false;
        if (list1 != null) {
            result1 = list1.removeIf(friendship -> Objects.equals(friendship.getFriendId(), friendId));
        }
        if (list2 != null) {
            result2 = list2.removeIf(friendship -> Objects.equals(friendship.getFriendId(), uid));
        }
        return result1 && result2;
    }
}
