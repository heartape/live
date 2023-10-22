package com.heartape.live.bullet.filter;

import com.heartape.live.bullet.connect.ConnectionManager;
import com.heartape.live.bullet.repository.bullet.Bullet;
import lombok.AllArgsConstructor;

/**
 * 直播间负载过滤器
 */
@AllArgsConstructor
public class LiveRoomLoadFilter implements Filter<Bullet> {

    /**
     * 最大长连接数
     */
    private final int max;

    private final ConnectionManager<Bullet> longConnectionManager;

    @Override
    public boolean permit(Bullet bullet) {
        int count = this.longConnectionManager.count();
        String uid = bullet.getUid();
        String roomId = bullet.getRoomId();
        return this.longConnectionManager.registered(roomId, uid) || count < this.max;
    }

}
