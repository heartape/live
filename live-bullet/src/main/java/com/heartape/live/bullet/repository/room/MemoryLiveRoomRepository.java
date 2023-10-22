package com.heartape.live.bullet.repository.room;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 内存库
 */
public class MemoryLiveRoomRepository implements LiveRoomRepository {

    private final Map<String, LiveRoom> map = new ConcurrentHashMap<>();

    @Override
    public void insert(LiveRoom liveRoom) {
        if (liveRoom != null){
            String id = liveRoom.getId();
            if (id != null && !id.isBlank()){
                this.map.put(id, liveRoom);
            }
        }
    }

    @Override
    public LiveRoom select(String id) {
        return this.map.get(id);
    }

    @Override
    public void delete(String id) {
        this.map.remove(id);
    }
}
