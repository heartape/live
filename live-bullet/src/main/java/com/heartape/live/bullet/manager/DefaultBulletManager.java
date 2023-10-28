package com.heartape.live.bullet.manager;

import com.heartape.live.bullet.connect.Connection;
import com.heartape.live.bullet.connect.ConnectionManager;
import com.heartape.live.bullet.filter.FilterChain;
import com.heartape.live.bullet.flow.FlowManager;
import com.heartape.live.bullet.repository.bullet.Bullet;
import com.heartape.live.bullet.repository.bullet.BulletRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.List;

@Slf4j
public class DefaultBulletManager implements BulletManager {

    private final ConnectionManager connectionManager;

    private final FlowManager flowManager;

    private final BulletRepository bulletRepository;

    private final FilterChain<Bullet> filterChain;

    public DefaultBulletManager(ConnectionManager connectionManager, FlowManager flowManager, BulletRepository bulletRepository, FilterChain<Bullet> filterChain) {
        this.connectionManager = connectionManager;
        this.flowManager = flowManager;
        this.bulletRepository = bulletRepository;
        this.filterChain = filterChain;
    }

    @Override
    public void register(Connection connection) {
        this.connectionManager.register(connection);
    }

    @Override
    public void logout(String roomId, String uid) {
        this.connectionManager.logout(roomId, uid);
    }

    @Override
    public void push(Bullet bullet) {
        if (bullet == null || !this.filterChain.permit(bullet)){
            return;
        }
        this.flowManager.push(bullet);
        this.bulletRepository.insert(bullet);
    }

    @Override
    public void push(Collection<Bullet> bullets) {
        bullets.forEach(this::push);
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
        private ConnectionManager connectionManager;
        private FlowManager flowManager;
        private BulletRepository bulletRepository;
        private FilterChain<Bullet> filterChain;

        public DefaultBulletManagerBuilder connectionManager(ConnectionManager connectionManager) {
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
