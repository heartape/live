package com.heartape.live.bullet.config;

import com.heartape.live.bullet.connect.ConnectionManager;
import com.heartape.live.bullet.flow.BulletThreadFlowManager;
import com.heartape.live.bullet.manager.BulletManager;
import com.heartape.live.bullet.manager.DefaultBulletManager;
import com.heartape.live.bullet.connect.MemoryConnectionManager;
import com.heartape.live.bullet.filter.*;
import com.heartape.live.bullet.flow.FlowManager;
import com.heartape.live.bullet.repository.bullet.Bullet;
import com.heartape.live.bullet.repository.bullet.BulletRepository;
import com.heartape.live.bullet.repository.bullet.RedisBulletRepository;
import com.heartape.live.bullet.repository.room.LiveRoomRepository;
import com.heartape.live.bullet.repository.room.LiveRoomStatusRepository;
import com.heartape.live.bullet.repository.room.MemoryLiveRoomRepository;
import com.heartape.live.bullet.repository.room.MemoryLiveRoomStatusRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Configuration
@EnableConfigurationProperties(LiveBulletProperties.class)
public class LiveBulletAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public BulletManager bulletManager(@Qualifier("bulletConnectionManager") ConnectionManager<Bullet> connectionManager,
                                           @Qualifier("bulletFlowManager") FlowManager flowManager,
                                           BulletRepository bulletRepository,
                                           @Qualifier("bulletFilterChain") FilterChain<Bullet> filterChain){
        return DefaultBulletManager.builder()
                .bulletRepository(bulletRepository)
                .flowManager(flowManager)
                .connectionManager(connectionManager)
                .filterChain(filterChain)
                .build();
    }

    /**
     * 弹幕配置
     */
    @Configuration
    protected static class BulletConfigurer {

        @Bean("bulletFlowManager")
        @ConditionalOnMissingBean
        public FlowManager flowManager(@Qualifier("bulletConnectionManager") ConnectionManager<Bullet> connectionManager,
                                       BulletRepository bulletRepository,
                                       LiveBulletProperties liveBulletProperties){
            LiveBulletProperties.Flow flow = liveBulletProperties.getFlow();
            return new BulletThreadFlowManager(connectionManager, bulletRepository, flow.getTime(), flow.getMax(), flow.getInit());
        }

        @Bean("bulletConnectionManager")
        @ConditionalOnMissingBean
        public ConnectionManager<Bullet> connectionManager(){
            return new MemoryConnectionManager<>();
        }

        @Bean
        public BulletRepository bulletRepository(RedisTemplate<String, Bullet> redisTemplate){
            return new RedisBulletRepository(redisTemplate);
        }

        @SuppressWarnings("deprecation")
        @Bean("bulletFilterChain")
        @ConditionalOnMissingBean
        public FilterChain<Bullet> filterChain(LiveBulletProperties liveBulletProperties,
                                               @Qualifier("bulletConnectionManager") ConnectionManager<Bullet> connectionManager,
                                               LiveRoomStatusRepository liveRoomStatusRepository){
            LiveBulletProperties.Filter filter = liveBulletProperties.getFilter();
            if (filter == null){
                return SimpleSerialFilterChain.builder().build();
            }

            List<String> authBlack = filter.getBlack();
            List<String> black = Objects.requireNonNullElseGet(authBlack, ArrayList::new);
            List<String> rooms = filter.getRoom();
            if (rooms != null && !rooms.isEmpty()){
                for (String room : rooms) {
                    liveRoomStatusRepository.insert(room);
                }
            }

            Integer maxLong = filter.getMaxLong();

            return SimpleSerialFilterChain
                    .builder()
                    .filter(new MemoryBlackListFilter(new HashSet<>(black)))
                    .filter(new LiveRoomStatusFilter(liveRoomStatusRepository))
                    .filter(new LiveRoomLoadFilter(maxLong, connectionManager))
                    .build();
        }
    }

    @Configuration
    protected static class LiveBasicConfigurer {

        @Bean
        @ConditionalOnMissingBean
        public LiveRoomRepository liveRoomRepository(){
            return new MemoryLiveRoomRepository();
        }

        @Bean
        @ConditionalOnMissingBean
        public LiveRoomStatusRepository liveRoomStatusRepository(){
            return new MemoryLiveRoomStatusRepository();
        }
    }
}
