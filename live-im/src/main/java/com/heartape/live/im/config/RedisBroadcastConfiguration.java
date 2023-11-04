package com.heartape.live.im.config;

import com.heartape.live.im.handler.RedisBroadcastClusterWebSocketSessionManager;
import com.heartape.live.im.handler.WebSocketSessionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import java.io.Serializable;
import java.util.Map;

// @Configuration
public class RedisBroadcastConfiguration {

    @Bean
    MessageDelegate listener(@Qualifier("standaloneWebSocketSessionManager") WebSocketSessionManager standaloneSessionManager) {
        return new RedisBroadcastClusterWebSocketSessionManager.DefaultMessageDelegate(standaloneSessionManager);
    }

    @Bean
    MessageListenerAdapter messageListenerAdapter(MessageDelegate listener) {
        return new MessageListenerAdapter(listener, "handleMessage");
    }

    @Bean
    RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory connectionFactory, MessageListenerAdapter listener) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listener, ChannelTopic.of(RedisBroadcastClusterWebSocketSessionManager.WEBSOCKET_USER_BROADCAST));
        return container;
    }

    public interface MessageDelegate {
        void handleMessage(String message);
        void handleMessage(Map message);
        void handleMessage(byte[] message);
        void handleMessage(Serializable message);
        void handleMessage(Serializable message, String channel);
    }
}
