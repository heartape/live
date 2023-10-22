package com.heartape.live.streaming.http;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.lang.NonNull;

/**
 * 路径需要严格匹配
 */
public class PrefixRequestMatcher implements RequestMatcher {

    private final String prefix;

    public PrefixRequestMatcher(@NonNull String prefix) {
        this.prefix = prefix;
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        return request.getServletPath().startsWith(prefix);
    }
}
