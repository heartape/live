package com.heartape.live.streaming.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterRegistration;
import jakarta.servlet.ServletContext;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.filter.DelegatingFilterProxy;

/**
 * 托管过滤器链到spring容器
 */
public abstract class AbstractLiveStreamingWebApplicationInitializer implements WebApplicationInitializer {

    /**
     * 默认的过滤器链名称
     */
    public static final String DEFAULT_FILTER_NAME = "liveFilterChain";

    /**
     * @see WebApplicationInitializer
     */
    @Override
    public void onStartup(@NonNull ServletContext servletContext) {
        String filterName = DEFAULT_FILTER_NAME;
        DelegatingFilterProxy filterChain = new DelegatingFilterProxy(filterName);
        registerFilter(servletContext, filterName, filterChain);
    }

    /**
     *
     * @param servletContext 上下文
     * @param filterName 过滤器名
     * @param filter 过滤器
     * @exception IllegalStateException if registration is null
     */
    private void registerFilter(ServletContext servletContext, String filterName, Filter filter) {
        FilterRegistration.Dynamic registration = servletContext.addFilter(filterName, filter);
        Assert.state(registration != null, () -> "Duplicate Filter registration for '" + filterName
                + "'. Check to ensure the Filter is only configured once.");
    }
}
