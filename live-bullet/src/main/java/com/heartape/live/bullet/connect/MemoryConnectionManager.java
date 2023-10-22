package com.heartape.live.bullet.connect;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * 长连接管理内存实现
 * @param <T> 推送的数据类型
 */
public class MemoryConnectionManager<T> implements ConnectionManager<T> {

    private final Map<String, Map<String, Connection<T>>> roomMap = new ConcurrentHashMap<>();

    /**
     * 一人能在多个直播间
     */
    private final Map<String, Set<String>> userMap = new ConcurrentHashMap<>();

    @Override
    public int count() {
        return this.userMap.size();
    }

    @Override
    public int roomCount() {
        return this.roomMap.size();
    }

    @Override
    public int count(String roomId) {
        Map<String, Connection<T>> userConnectionMap = this.roomMap.get(roomId);
        return userConnectionMap == null ? 0 : userConnectionMap.size();
    }

    @Override
    public void register(Connection<T> connection){
        String roomId = connection.getRoomId();
        String uid = connection.getUid();
        // roomMap
        Map<String, Connection<T>> roomConnectionMap = this.roomMap.get(roomId);
        if (roomConnectionMap == null){
            roomConnectionMap = new ConcurrentHashMap<>();
            roomConnectionMap.put(uid, connection);
            this.roomMap.put(roomId, roomConnectionMap);
        } else {
            roomConnectionMap.put(uid, connection);
        }

        // userMap
        Set<String> roomIds = this.userMap.get(uid);
        if (roomIds == null){
            roomIds = new ConcurrentSkipListSet<>();
            roomIds.add(roomId);
            this.userMap.put(uid, roomIds);
        } else {
            roomIds.add(roomId);
        }
    }

    @Override
    public boolean registered(String roomId, String uid) {
        Map<String, Connection<T>> roomConnectionMap = this.roomMap.get(roomId);
        return roomConnectionMap != null && roomConnectionMap.containsKey(uid);
    }

    @Override
    public Connection<T> pick(String roomId, String uid){
        Map<String, Connection<T>> roomConnectionMap = this.roomMap.get(roomId);
        return roomConnectionMap == null ? null : roomConnectionMap.get(uid);
    }

    @Override
    public Map<String, Connection<T>> pickRoom(String roomId){
        return this.roomMap.get(roomId);
    }

    /**
     * 根据hash取key
     * @return 当前Flow需要推送的房间
     */
    @Override
    public Map<String, Map<String, Connection<T>>> pickRoom(int seat, int range){
        Map<String, Map<String, Connection<T>>> res = new HashMap<>();
        this.roomMap.forEach((roomId, connectionMap) -> {
            if ((roomId.hashCode() & range - 1) == seat && connectionMap != null && !connectionMap.isEmpty()){
                res.put(roomId, connectionMap);
            }
        });
        return res;
    }

    @Override
    public int seat(String roomId, int range) {
        return roomId.hashCode() & range - 1;
    }

    @Override
    public void upgrade(String roomId, String uid) {

    }

    @Override
    public void relegation(String roomId, String uid) {

    }

    @Override
    public void logout(String roomId, String uid){
        // roomMap
        Map<String, Connection<T>> roomConnectionMap = this.roomMap.get(roomId);
        if (roomConnectionMap != null){
            Connection<T> connection = roomConnectionMap.remove(uid);
            if (connection != null){
                logout(connection);
            }
        }

        // userMap
        Set<String> uidSet = this.userMap.get(uid);
        if (uidSet != null){
            uidSet.remove(roomId);
        }
    }

    @Override
    public void logout(Connection<T> connection) {

    }
}
