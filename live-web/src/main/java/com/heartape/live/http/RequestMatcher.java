package com.heartape.live.http;

import jakarta.servlet.http.HttpServletRequest;

public interface RequestMatcher {

    boolean matches(HttpServletRequest request);

}
