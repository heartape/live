package com.heartape.live.streaming.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface LiveStreamingFilterChain {

    /**
     * 对当前请求进行匹配
     * @param request 请求
     * @return 是否适用于当前过滤器链
     */
    boolean matches(HttpServletRequest request);

    /**
     * @return 过滤器链下的所有过滤器
     */
    List<Filter> getFilters();
}
