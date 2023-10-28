package com.heartape.live.bullet.manager;

import com.heartape.live.bullet.connect.Connection;
import com.heartape.live.bullet.repository.bullet.Bullet;

import java.util.Collection;
import java.util.List;

/**
 * 弹幕管理推拉结合:
 * <ul>
 *     <li>长连接推送
 *     <li>持久化
 *     <li>长连接不稳定会降级为短链接，主动拉流
 * </ul>
 */
public interface BulletManager {

    void register(Connection connection);

    void logout(String roomId, String uid);

    void push(Bullet bullet);

    void push(Collection<Bullet> bullets);

    /**
     * 短连接拉取:对于长连接的补充，在网络环境恶劣的情况下避免长连接持续重试
     */
    List<Bullet> pull(String roomId);

}
