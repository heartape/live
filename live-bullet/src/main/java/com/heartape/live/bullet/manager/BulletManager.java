package com.heartape.live.bullet.manager;

import com.heartape.live.bullet.connect.Connection;
import com.heartape.live.bullet.repository.bullet.Bullet;

import java.util.Collection;
import java.util.List;

/**
 * 弹幕管理，整合各个组件，暴露给外部使用
 */
public interface BulletManager {

    void register(Connection<Bullet> connection);

    void logout(String roomId, String uid);

    void push(Bullet bullet);

    void push(Collection<Bullet> bullets);

    /**
     * 短连接拉取:对于长连接的补充，在网络环境恶劣的情况下避免长连接持续重试
     */
    List<Bullet> pull();

}
