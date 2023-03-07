package com.heartape.live.filter;

import com.heartape.live.http.RequestMatcher;
import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;

import java.util.List;

@Getter
public class DefaultMediaFilterChain implements MediaFilterChain {

    private final RequestMatcher requestMatcher;

    private final List<Filter> filters;

    public DefaultMediaFilterChain(RequestMatcher requestMatcher, List<Filter> filters) {
        this.requestMatcher = requestMatcher;
        this.filters = filters;
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        return requestMatcher.matches(request);
    }

}
