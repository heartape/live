package com.heartape.live.streaming.config;

import com.heartape.live.streaming.code.CodeAuthenticationProvider;
import com.heartape.live.streaming.code.HttpCodeAuthenticationProvider;
import com.heartape.live.streaming.filter.AbstractLiveStreamingWebApplicationInitializer;
import com.heartape.live.streaming.filter.DefaultLiveStreamingFilterChain;
import com.heartape.live.streaming.filter.LiveStreamingFilterChain;
import com.heartape.live.streaming.filter.LiveStreamingFilterChainProxy;
import com.heartape.live.streaming.filter.srs.SrsPlayEndpointFilter;
import com.heartape.live.streaming.filter.srs.SrsPublishEndpointFilter;
import com.heartape.live.streaming.filter.zlmediakit.ZlmPlayEndpointFilter;
import com.heartape.live.streaming.filter.zlmediakit.ZlmPublishEndpointFilter;
import com.heartape.live.streaming.http.ConvertedResponseHttpMessageConverter;
import com.heartape.live.streaming.http.PrefixRequestMatcher;
import com.heartape.live.streaming.facade.DefaultLiveStreamingHookFacade;
import com.heartape.live.streaming.facade.LiveStreamingHookFacade;
import jakarta.servlet.Filter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * 配置流媒体服务器自动配置
 * @since 0.1
 * @author heartape
 */
@Configuration
@EnableConfigurationProperties({LiveStreamingProperties.class})
public class LiveStreamingAutoConfiguration {

    /**
     * 流媒体过滤器链配置
     * @since 0.1
     * @author heartape
     */
    @Configuration
    protected static class LiveStreamingFilterRegisterChainConfigurer {

        /**
         * 根据配置创建过滤器链代理
         * @param liveStreamingFilterChain 过滤器链
         * @param liveStreamingProperties 配置
         * @return 过滤器链代理
         * @exception IllegalArgumentException 检查是否有type配置
         */
        @Bean(name = AbstractLiveStreamingWebApplicationInitializer.DEFAULT_FILTER_NAME)
        @Order(1)
        public Filter liveMediaFilterChain(LiveStreamingFilterChain liveStreamingFilterChain, LiveStreamingProperties liveStreamingProperties) {
            if (liveStreamingProperties.getType() == null){
                throw new IllegalArgumentException("检查type配置");
            }
            List<LiveStreamingFilterChain> liveStreamingFilterChains = new ArrayList<>();
            liveStreamingFilterChains.add(liveStreamingFilterChain);
            return new LiveStreamingFilterChainProxy(liveStreamingFilterChains);
        }
    }

    /**
     * 基础组件配置
     * @since 0.1
     * @author heartape
     */
    @Configuration
    protected static class LiveStreamingBaseConfiguration {

        @Bean
        @ConditionalOnMissingBean
        public RestOperations restOperations(){
            return new RestTemplate();
        }
    }

    /**
     * 流程管理配置
     * @since 0.1
     * @author heartape
     */
    @Configuration
    protected static class LiveStreamingManageConfiguration {

        @Bean
        @ConditionalOnMissingBean
        public CodeAuthenticationProvider codeAuthenticationProvider(RestOperations restOperations, LiveStreamingProperties liveStreamingProperties){
            return new HttpCodeAuthenticationProvider(restOperations, liveStreamingProperties.getCode().getUrl());
        }

        @Bean
        @ConditionalOnMissingBean
        public LiveStreamingHookFacade liveStreamingHookFacade(JwtDecoder jwtDecoder, CodeAuthenticationProvider codeAuthenticationProvider){
            return new DefaultLiveStreamingHookFacade(jwtDecoder, codeAuthenticationProvider);
        }
    }

    /**
     * 流媒体过滤器配置
     * @since 0.1
     * @author heartape
     */
    @Configuration
    protected static class LiveStreamingFilterRegisterConfigurer {

        @Bean
        @ConditionalOnProperty(name = "live.streaming.type", havingValue = "srs")
        public LiveStreamingFilterChain srsFilterChain(LiveStreamingHookFacade liveStreamingHookFacade){

            ConvertedResponseHttpMessageConverter convertedResponseHttpMessageConverter = new ConvertedResponseHttpMessageConverter();
            SrsPlayEndpointFilter srsPlayEndpointFilters = new SrsPlayEndpointFilter(convertedResponseHttpMessageConverter, liveStreamingHookFacade);
            SrsPublishEndpointFilter srsPublishEndpointFilter = new SrsPublishEndpointFilter(convertedResponseHttpMessageConverter, liveStreamingHookFacade);

            List<Filter> filters = new ArrayList<>();
            filters.add(srsPlayEndpointFilters);
            filters.add(srsPublishEndpointFilter);

            PrefixRequestMatcher prefixRequestMatcher = new PrefixRequestMatcher("/live/streaming/hook");
            return new DefaultLiveStreamingFilterChain(prefixRequestMatcher, filters);
        }

        @Bean
        @ConditionalOnProperty(name = "live.streaming.type", havingValue = "zlm")
        public LiveStreamingFilterChain zlmFilterChain(LiveStreamingHookFacade liveStreamingHookFacade){

            ConvertedResponseHttpMessageConverter mediaResponseHttpMessageConverter = new ConvertedResponseHttpMessageConverter();
            ZlmPlayEndpointFilter zlmPlayEndpointFilter = new ZlmPlayEndpointFilter(mediaResponseHttpMessageConverter, liveStreamingHookFacade);
            ZlmPublishEndpointFilter zlmPublishEndpointFilter = new ZlmPublishEndpointFilter(mediaResponseHttpMessageConverter, liveStreamingHookFacade);

            List<Filter> filters = new ArrayList<>();
            filters.add(zlmPlayEndpointFilter);
            filters.add(zlmPublishEndpointFilter);

            PrefixRequestMatcher prefixRequestMatcher = new PrefixRequestMatcher("/live/streaming/hook");
            return new DefaultLiveStreamingFilterChain(prefixRequestMatcher, filters);
        }
    }

}
