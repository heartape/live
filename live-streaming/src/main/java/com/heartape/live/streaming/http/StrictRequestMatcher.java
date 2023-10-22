package com.heartape.live.streaming.http;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpMethod;
import org.springframework.lang.NonNull;

/**
 * 路径需要严格匹配
 */
public class StrictRequestMatcher implements RequestMatcher {

    private final String uri;
    private final HttpMethod httpMethod;

    public StrictRequestMatcher(@NonNull String uri, HttpMethod httpMethod) {
        this.uri = uri;
        this.httpMethod = httpMethod;
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        return this.httpMethod == HttpMethod.valueOf(request.getMethod()) && this.uri.equals(request.getServletPath());
    }
}
