package com.heartape.live.im.config;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Enumeration;
import java.util.Map;

public class CookieAuthHttpSessionHandshakeInterceptor extends HttpSessionHandshakeInterceptor {

    public final static String WS_IM_TOKEN = "ws-im-token";

    @SuppressWarnings("NullableProblems")
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) {
        HttpSession session = getSession(request);
        if (session != null) {
            Authentication authentication = (Authentication)session.getAttribute(WS_IM_TOKEN);
            if (authentication == null || !authentication.isAuthenticated()){
                return false;
            }
            SecurityContextHolder.getContext().setAuthentication(authentication);
            if (isCopyHttpSessionId()) {
                attributes.put(HTTP_SESSION_ID_ATTR_NAME, session.getId());
            }
            Enumeration<String> names = session.getAttributeNames();
            while (names.hasMoreElements()) {
                String name = names.nextElement();
                if (isCopyAllAttributes() || getAttributeNames().contains(name)) {
                    attributes.put(name, session.getAttribute(name));
                }
            }
            return true;
        }
        return false;
    }

    @Nullable
    private HttpSession getSession(ServerHttpRequest request) {
        if (request instanceof ServletServerHttpRequest serverRequest) {
            return serverRequest.getServletRequest().getSession(isCreateSession());
        }
        return null;
    }
}
