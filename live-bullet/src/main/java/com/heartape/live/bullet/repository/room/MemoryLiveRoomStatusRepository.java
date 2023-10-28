package com.heartape.live.bullet.repository.room;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 内存库
 */
public class MemoryLiveRoomStatusRepository implements LiveRoomStatusRepository {

    private final Map<String, Status> liveRoomStatusMap = new ConcurrentHashMap<>();

    @Override
    public void insert(String id) {
        liveRoomStatusMap.put(id, Status.NORMAL);
    }

    @Override
    public void delete(String id) {
        liveRoomStatusMap.put(id, Status.STOP);
    }

    @Override
    public void ban(String id) {
        liveRoomStatusMap.put(id, Status.BAN);
    }

    @Override
    public boolean exist(String id, Status status) {
        return this.liveRoomStatusMap.get(id) == status;
    }
}
