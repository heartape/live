package com.heartape.live.bullet.flow;

import com.heartape.live.bullet.connect.Connection;
import com.heartape.live.bullet.connect.ConnectionManager;
import com.heartape.live.bullet.repository.bullet.Bullet;
import com.heartape.live.bullet.repository.bullet.BulletRepository;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 弹幕流
 */
@Slf4j
public class BulletThreadFlowManager extends AbstractThreadFlowManager {

    private final ConnectionManager<Bullet> connectionManager;

    private final BulletRepository bulletRepository;

    public BulletThreadFlowManager(ConnectionManager<Bullet> connectionManager,
                                   BulletRepository bulletRepository,
                                   int time, int maxFlowSizePow, int coreFlowSizePow) {
        super(time, maxFlowSizePow, coreFlowSizePow);
        this.connectionManager = connectionManager;
        this.bulletRepository = bulletRepository;
        setFlowSize(coreFlowSizePow);
    }

    /**
     * todo: 优化对于空闲直播间的判定与处理
     */
    @Override
    protected boolean doTaskCore(int seat, int flowSize, long start, long end) {
        Map<String, Map<String, Connection<Bullet>>> roomMap = this.connectionManager.pickRoom(seat, flowSize);
        if (roomMap.isEmpty()){
            return true;
        }

        roomMap.forEach((roomId, connectionMap) -> {
            List<Bullet> bullets = this.bulletRepository.select(roomId, start, end);
            if (bullets == null || bullets.isEmpty()) {
                log.debug("room:{} is Empty", roomId);
                return;
            }
            connectionMap.forEach((uid, longConnection) -> {
                if (longConnection == null) {
                    connectionMap.remove(uid);
                } else if (longConnection.isCompleted()){
                    this.connectionManager.logout(roomId, uid);
                    connectionMap.remove(uid);
                } else {
                    try {
                        longConnection.send(bullets, end);
                    } catch (IOException e) {
                        this.connectionManager.logout(roomId, uid);
                    }
                }
            });
        });

        return true;
    }
}
