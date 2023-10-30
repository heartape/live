package com.heartape.live.im.manage.friend;

import com.heartape.util.id.IdentifierGenerator;
import lombok.AllArgsConstructor;

/**
 * 默认实现
 * @see FriendshipService
 */
@AllArgsConstructor
public class DefaultFriendshipService implements FriendshipService {

    private final IdentifierGenerator<Long> identifierGenerator;

    private final FriendshipRepository friendshipRepository;

    private final FriendshipGroupRepository friendshipGroupRepository;

    @Override
    public void addGroup(String uid, String name) {
        String id = this.identifierGenerator.nextId().toString();
        FriendshipGroup friendshipGroup = new FriendshipGroup(id, uid, name);
        this.friendshipGroupRepository.insert(friendshipGroup);
    }

    @Override
    public void removeGroup(String uid, String groupId) {
        this.friendshipGroupRepository.delete(groupId, uid);
    }

    @Override
    public void renameGroup(String groupId, String uid, String name) {
        this.friendshipGroupRepository.update(groupId, name, uid);
    }

    @Override
    public void addFriend(String uid, String friendId, String groupId, String friendGroupId) {
        this.friendshipRepository.insert(uid, friendId, groupId, friendGroupId);
    }

    @Override
    public boolean isFriend(String uid, String friendId) {
        return this.friendshipRepository.isFriend(uid, friendId);
    }

    @Override
    public void updateFriendGroup(String uid, String friendId, String groupId) {
        this.friendshipRepository.update(uid, friendId, groupId);
    }

    @Override
    public void removeFriend(String uid, String friendId) {
        this.friendshipRepository.delete(uid, friendId);
    }

    @Override
    public void addBlackList(String uid, String friendId) {
        this.friendshipRepository.update(uid, friendId, -1);
    }

    @Override
    public void removeBlackList(String uid, String friendId) {
        this.friendshipRepository.update(uid, friendId, 1);
    }
}
