package com.heartape.live.bullet.repository.bullet;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * redis存储实现
 */
@Component
@AllArgsConstructor
public class RedisBulletRepository implements BulletRepository {

    private final RedisTemplate<String, Bullet> redisTemplate;

    /**
     * 单个插入性能较低
     * @param bullet 弹幕
     */
    @Override
    public void insert(Bullet bullet) {
        if (bullet != null){
            ZSetOperations<String, Bullet> opsForZSet = redisTemplate.opsForZSet();
            opsForZSet.add(bullet.getRoomId(), bullet, (double) bullet.getTimestamp());
        }
    }

    @Override
    public void insert(Collection<Bullet> bullets){
        if (bullets != null && !bullets.isEmpty()){
            ZSetOperations<String, Bullet> opsForZSet = redisTemplate.opsForZSet();

            Map<String, Set<ZSetOperations.TypedTuple<Bullet>>> bulletChatMap = bullets
                    .stream()
                    .collect(Collectors.groupingBy(Bullet::getRoomId,
                            Collectors.mapping(bulletChat -> (ZSetOperations.TypedTuple<Bullet>)new DefaultTypedTuple<>(bulletChat, (double) bulletChat.getTimestamp()),
                                    Collectors.toSet())));

            bulletChatMap.forEach(opsForZSet::add);
        }
    }

    @Override
    public List<Bullet> select(String room, long start, long end){
        ZSetOperations<String, Bullet> opsForZSet = redisTemplate.opsForZSet();
        Set<Bullet> bullets = opsForZSet.rangeByScore(room, (double) start, (double) end);
        return CollectionUtils.isEmpty(bullets) ? new ArrayList<>() : new ArrayList<>(bullets);
    }

}
