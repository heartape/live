package com.heartape.live.im.config;

import com.heartape.live.im.gateway.Gateway;
import com.heartape.live.im.handler.*;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

/**
 * @see <a href="https://docs.spring.io/spring-framework/reference/web/websocket.html">docs.spring.io</a>
 */
@AllArgsConstructor
@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {

    private final Gateway gateway;

    private final RedisOperations<String, String> redisOperations;

    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxTextMessageBufferSize(8192);
        container.setMaxBinaryMessageBufferSize(8192);
        return container;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        WebSocketSessionManager webSocketSessionManager = new ClusterWebSocketSessionManager(redisOperations, new ClusterWebSocketHandler());
        registry.addHandler(new GroupTextWebSocketHandler(gateway), "/ws/group")
                .addHandler(new PersonTextWebSocketHandler(gateway, webSocketSessionManager), "/ws/person")
                .addInterceptors(new HttpSessionHandshakeInterceptor())
                .setHandshakeHandler(new AuthenticationToSessionHandshakeHandler())
                .setAllowedOriginPatterns("*");
    }

}
