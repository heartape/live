package com.heartape.live.bullet.repository.bullet;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 内存库
 */
public class MemoryBulletRepository implements BulletRepository {

    private final Map<String, Queue<Bullet>> queueMap = new ConcurrentHashMap<>();

    @Override
    public void insert(Bullet bullet) {
        if (bullet != null){
            String roomId = bullet.getRoomId();
            Queue<Bullet> queue = this.queueMap.get(roomId);
            if (queue == null){
                queue = new ConcurrentLinkedQueue<>();
                queue.offer(bullet);
                this.queueMap.put(roomId, queue);
            } else {
                queue.offer(bullet);
            }
        }
    }

    @Override
    public void insert(Collection<Bullet> bullets){
        if (bullets != null && !bullets.isEmpty()){
            for (Bullet bullet : bullets) {
                insert(bullet);
            }
        }
    }

    @Override
    public List<Bullet> select(String room, long start, long end){
        Queue<Bullet> queue = this.queueMap.get(room);
        if (queue == null || queue.isEmpty()){
            return null;
        }
        List<Bullet> list = new ArrayList<>();
        Bullet bullet;
        while ((bullet =  queue.poll()) != null){
            list.add(bullet);
        }
        return list;
    }
}
