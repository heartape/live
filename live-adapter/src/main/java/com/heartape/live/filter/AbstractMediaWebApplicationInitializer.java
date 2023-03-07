package com.heartape.live.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterRegistration;
import jakarta.servlet.ServletContext;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.filter.DelegatingFilterProxy;

public abstract class AbstractMediaWebApplicationInitializer implements WebApplicationInitializer {

    public static final String DEFAULT_FILTER_NAME = "liveFilterChain";

    @Override
    public void onStartup(@NonNull ServletContext servletContext) {
        String filterName = DEFAULT_FILTER_NAME;
        DelegatingFilterProxy filterChain = new DelegatingFilterProxy(filterName);
        registerFilter(servletContext, filterName, filterChain);
    }

    private void registerFilter(ServletContext servletContext, String filterName, Filter filter) {
        FilterRegistration.Dynamic registration = servletContext.addFilter(filterName, filter);
        Assert.state(registration != null, () -> "Duplicate Filter registration for '" + filterName
                + "'. Check to ensure the Filter is only configured once.");
    }
}
