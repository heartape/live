package com.heartape.live.bullet.repository.bullet;

import java.util.Collection;
import java.util.List;

/**
 * 弹幕存储
 */
public interface BulletRepository {

    /**
     * 单个插入数据
     * @param bullet 弹幕
     */
    void insert(Bullet bullet);

    /**
     * 批量插入数据
     * @param bullets 弹幕list
     */
    void insert(Collection<Bullet> bullets);

    /**
     * 根据时间获取弹幕
     * @param roomId 直播间id
     * @param start 开始时间戳
     * @param end 结束时间戳
     * @return 弹幕list
     */
    List<Bullet> select(String roomId, long start, long end);

}
