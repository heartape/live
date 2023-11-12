package com.heartape.live.im.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
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

    private final WebSocketHandler imWebSocketHandler;

    private final WebSocketHandler clusterWebSocketHandler;

    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxTextMessageBufferSize(8192);
        container.setMaxBinaryMessageBufferSize(8192);
        return container;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(imWebSocketHandler, "/im")
                .addHandler(clusterWebSocketHandler, "/cluster")
                .addInterceptors(new HttpSessionHandshakeInterceptor())
                .setHandshakeHandler(new AuthenticationToSessionHandshakeHandler())
                .setAllowedOriginPatterns("*");
    }

}
