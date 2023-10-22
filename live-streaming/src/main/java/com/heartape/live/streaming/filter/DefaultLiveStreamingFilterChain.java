package com.heartape.live.streaming.filter;

import com.heartape.live.streaming.http.RequestMatcher;
import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;

import java.util.List;

/**
 * 默认MediaFilterChain
 */
@Getter
public class DefaultLiveStreamingFilterChain implements LiveStreamingFilterChain {

    /**
     * 请求匹配
     */
    private final RequestMatcher requestMatcher;

    /**
     * 过滤器
     */
    private final List<Filter> filters;

    /**
     * @param requestMatcher 请求匹配
     * @param filters 过滤器
     */
    public DefaultLiveStreamingFilterChain(RequestMatcher requestMatcher, List<Filter> filters) {
        this.requestMatcher = requestMatcher;
        this.filters = filters;
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        return requestMatcher.matches(request);
    }

}
