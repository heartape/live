package com.heartape.live.bullet.manager;

import com.heartape.live.bullet.connect.Connection;
import com.heartape.live.bullet.connect.ConnectionManager;
import com.heartape.live.bullet.filter.FilterChain;
import com.heartape.live.bullet.flow.Flow;
import com.heartape.live.bullet.flow.FlowManager;
import com.heartape.live.bullet.repository.bullet.Bullet;
import com.heartape.live.bullet.repository.bullet.BulletRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class DefaultBulletManager implements BulletManager {

    private final ConnectionManager<Bullet> connectionManager;

    private final FlowManager flowManager;

    private final BulletRepository bulletRepository;

    private final FilterChain<Bullet> filterChain;

    public DefaultBulletManager(ConnectionManager<Bullet> connectionManager, FlowManager flowManager, BulletRepository bulletRepository, FilterChain<Bullet> filterChain) {
        this.connectionManager = connectionManager;
        this.flowManager = flowManager;
        this.bulletRepository = bulletRepository;
        this.filterChain = filterChain;
    }

    @Override
    public void register(Connection<Bullet> connection) {
        this.connectionManager.register(connection);
    }

    @Override
    public void logout(String roomId, String uid) {
        this.connectionManager.logout(roomId, uid);
    }

    @Override
    public void push(Bullet bullet) {
        if (bullet != null && this.filterChain.permit(bullet)){
            this.bulletRepository.insert(bullet);
            next(bullet.getRoomId());
        }
    }

    /**
     * 唤醒
     */
    private void next(String roomId){
        int seat = this.connectionManager.seat(roomId, this.flowManager.getFlowSize());
        Flow flow = this.flowManager.getFlow(seat);
        flow.next();
    }

    @Override
    public void push(Collection<Bullet> bullets) {
        List<Bullet> list = bullets.stream()
                .filter(this.filterChain::permit)
                .collect(Collectors.toList());
        this.bulletRepository.insert(list);
        bullets.stream()
                .map(Bullet::getRoomId)
                .collect(Collectors.toSet())
                .forEach(this::next);
    }

    @Override
    public List<Bullet> pull(String roomId) {
        long l = System.currentTimeMillis();
        return this.bulletRepository.select(roomId, l - 1000, l);
    }

    public static DefaultBulletManagerBuilder builder() {
        return new DefaultBulletManagerBuilder();
    }

    public static class DefaultBulletManagerBuilder {
        private ConnectionManager<Bullet> connectionManager;
        private FlowManager flowManager;
        private BulletRepository bulletRepository;
        private FilterChain<Bullet> filterChain;

        public DefaultBulletManagerBuilder connectionManager(ConnectionManager<Bullet> connectionManager) {
            this.connectionManager = connectionManager;
            return this;
        }

        public DefaultBulletManagerBuilder flowManager(FlowManager flowManager) {
            this.flowManager = flowManager;
            return this;
        }

        public DefaultBulletManagerBuilder bulletRepository(BulletRepository bulletRepository) {
            this.bulletRepository = bulletRepository;
            return this;
        }

        public DefaultBulletManagerBuilder filterChain(FilterChain<Bullet> filterChain) {
            this.filterChain = filterChain;
            return this;
        }

        public DefaultBulletManager build() {
            return new DefaultBulletManager(this.connectionManager, this.flowManager, this.bulletRepository, this.filterChain);
        }
    }
}
