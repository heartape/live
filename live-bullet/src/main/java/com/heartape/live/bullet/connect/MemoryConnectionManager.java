package com.heartape.live.bullet.connect;

import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 长连接管理内存实现
 */
public class MemoryConnectionManager implements ConnectionManager {

    private final Map<String, Map<String, Connection>> roomMap = new ConcurrentHashMap<>();

    @Override
    public int count() {
        return this.roomMap.keySet()
                .stream()
                .mapToInt(this::count)
                .sum();
    }

    @Override
    public int roomCount() {
        return this.roomMap.size();
    }

    @Override
    public int count(String roomId) {
        Map<String, Connection> userConnectionMap = this.roomMap.get(roomId);
        return userConnectionMap == null ? 0 : userConnectionMap.size();
    }

    @Override
    public void register(String roomId) {
        this.roomMap.putIfAbsent(roomId, new ConcurrentHashMap<>());
    }

    @Override
    public void register(Connection connection){
        String roomId = connection.getRoomId();
        if (!this.roomMap.containsKey(roomId)){
            throw new RuntimeException("roomId not exist");
        }
        String uid = connection.getUid();
        // roomMap
        this.roomMap.get(roomId).put(uid, connection);
    }

    @Override
    public boolean registered(String roomId, String uid) {
        Map<String, Connection> roomConnectionMap = this.roomMap.get(roomId);
        return roomConnectionMap != null && roomConnectionMap.containsKey(uid);
    }

    @Override
    public void push(String roomId, Object o) {
        long timestamp = System.currentTimeMillis();
        Map<String, Connection> userMap = this.roomMap.get(roomId);
        if (!CollectionUtils.isEmpty(userMap)){
            for (Connection connection : userMap.values()) {
                if (connection.isCompleted()){
                    this.roomMap.remove(connection.getUid());
                } else {
                    connection.send(o, timestamp);
                }
            }
        }
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
        Map<String, Connection> roomConnectionMap = this.roomMap.get(roomId);
        if (roomConnectionMap != null){
            Connection connection = roomConnectionMap.remove(uid);
            if (connection != null){
                logout(connection);
            }
        }
    }

    @Override
    public void logout(Connection connection) {
        logout(connection.getRoomId(), connection.getUid());
    }
}
