package com.heartape.live.im.config;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;
import java.util.Objects;

/**
 * websocket认证
 */
public class AuthenticationToSessionHandshakeHandler extends DefaultHandshakeHandler {
    @SuppressWarnings("NullableProblems")
    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        HttpSession session = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest().getSession();
        return (Principal)session.getAttribute("LIVE_" + HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
    }
}
