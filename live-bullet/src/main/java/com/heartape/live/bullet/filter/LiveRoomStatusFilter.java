package com.heartape.live.bullet.filter;

import com.heartape.live.bullet.repository.bullet.Bullet;
import com.heartape.live.bullet.repository.room.LiveRoomStatusRepository;
import lombok.AllArgsConstructor;

/**
 * 直播间状态过滤器
 */
@AllArgsConstructor
public class LiveRoomStatusFilter implements Filter<Bullet> {

    private final LiveRoomStatusRepository liveRoomStatusRepository;

    @Override
    public boolean permit(Bullet bullet) {
        return liveRoomStatusRepository.exist(bullet.getRoomId(), LiveRoomStatusRepository.Status.NORMAL);
    }

}
