package com.heartape.live.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.List;

/**
 * 过滤器链处理,将多个过滤器链封装成一个过滤器
 */
public class MediaFilterChainProxy extends GenericFilterBean {

    private final List<MediaFilterChain> mediaFilterChains;

    public MediaFilterChainProxy(List<MediaFilterChain> mediaFilterChains) {
        this.mediaFilterChains = mediaFilterChains;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        MediaFilterChain mediaFilterChain = null;
        for (MediaFilterChain filterChain : mediaFilterChains) {
            if (filterChain.matches(httpServletRequest)){
                mediaFilterChain = filterChain;
                break;
            }
        }
        if (mediaFilterChain == null){
            chain.doFilter(request, response);
            return;
        }
        List<Filter> filters = mediaFilterChain.getFilters();
        VirtualFilterChain virtualFilterChain = new VirtualFilterChain(chain, filters);
        virtualFilterChain.doFilter(request, response);
    }

    @Slf4j
    private static final class VirtualFilterChain implements FilterChain {

        private final FilterChain originalChain;

        private final List<Filter> additionalFilters;

        private final int size;

        private int currentPosition = 0;

        private VirtualFilterChain(FilterChain chain, List<Filter> additionalFilters) {
            this.originalChain = chain;
            this.additionalFilters = additionalFilters;
            this.size = additionalFilters.size();
        }

        @Override
        public void doFilter(ServletRequest request, ServletResponse response) throws IOException, ServletException {
            if (this.currentPosition == this.size) {
                this.originalChain.doFilter(request, response);
                return;
            }
            this.currentPosition++;
            Filter nextFilter = this.additionalFilters.get(this.currentPosition - 1);
            if (log.isTraceEnabled()) {
                String name = nextFilter.getClass().getSimpleName();
                log.trace("Invoking {} ({}/{})", name, this.currentPosition, this.size);
            }
            nextFilter.doFilter(request, response, this);
        }
    }
}
