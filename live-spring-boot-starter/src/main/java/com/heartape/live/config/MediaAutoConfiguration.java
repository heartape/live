package com.heartape.live.config;

import com.heartape.live.code.MemoryPublishCodeRepository;
import com.heartape.live.code.PublishCodeRepository;
import com.heartape.live.facade.MediaHookFacade;
import com.heartape.live.facade.RepositoryMediaHookFacade;
import com.heartape.live.filter.AbstractMediaWebApplicationInitializer;
import com.heartape.live.filter.DefaultMediaFilterChain;
import com.heartape.live.filter.MediaFilterChain;
import com.heartape.live.filter.MediaFilterChainProxy;
import com.heartape.live.filter.srs.SrsPlayEndpointFilter;
import com.heartape.live.filter.srs.SrsPublishEndpointFilter;
import com.heartape.live.filter.zlmediakit.ZlmPlayEndpointFilter;
import com.heartape.live.filter.zlmediakit.ZlmPublishEndpointFilter;
import com.heartape.live.http.ConvertedResponseHttpMessageConverter;
import com.heartape.live.http.PrefixRequestMatcher;
import com.heartape.live.scope.MemoryUserScopeRepository;
import com.heartape.live.scope.UserScopeRepository;
import com.heartape.live.web.PublishCodeSetEndpointFilter;
import com.heartape.live.web.UserScopeSetEndpointFilter;
import jakarta.servlet.Filter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * 配置流媒体服务器自动配置
 * @since 0.1
 * @author heartape
 */
@Configuration
@EnableConfigurationProperties({MediaProperties.class})
@Import({HttpConfigurer.class})
public class MediaAutoConfiguration {

    /**
     * 流媒体过滤器链配置
     * @since 0.1
     * @author heartape
     */
    @Configuration
    protected static class MediaFilterRegisterChainConfigurer {
        @Bean(name = AbstractMediaWebApplicationInitializer.DEFAULT_FILTER_NAME)
        @Order(1)
        public Filter liveMediaFilterChain(PublishCodeRepository publishCodeRepository, UserScopeRepository userScopeRepository, MediaFilterChain mediaFilterChain, MediaProperties mediaProperties) {
            if (mediaProperties.getType() == null){
                throw new IllegalArgumentException("检查spring.live.type配置");
            }
            List<MediaFilterChain> mediaFilterChains = new ArrayList<>();
            mediaFilterChains.add(mediaFilterChain);
            mediaFilterChains.add(mediaManageFilterChain(publishCodeRepository, userScopeRepository));
            return new MediaFilterChainProxy(mediaFilterChains);
        }

        private MediaFilterChain mediaManageFilterChain(PublishCodeRepository publishCodeRepository, UserScopeRepository userScopeRepository){
            ConvertedResponseHttpMessageConverter convertedResponseHttpMessageConverter = new ConvertedResponseHttpMessageConverter();
            PublishCodeSetEndpointFilter publishCodeSetEndpointFilter = new PublishCodeSetEndpointFilter(convertedResponseHttpMessageConverter, publishCodeRepository);
            UserScopeSetEndpointFilter userScopeSetEndpointFilter = new UserScopeSetEndpointFilter(convertedResponseHttpMessageConverter, userScopeRepository);

            List<Filter> filters = new ArrayList<>();
            filters.add(publishCodeSetEndpointFilter);
            filters.add(userScopeSetEndpointFilter);

            PrefixRequestMatcher prefixRequestMatcher = new PrefixRequestMatcher("/live/manage");
            return new DefaultMediaFilterChain(prefixRequestMatcher, filters);
        }
    }

    /**
     * 存储库配置
     * @since 0.1
     * @author heartape
     */
    @Configuration
    protected static class MediaManageConfiguration {

        @Bean
        @ConditionalOnMissingBean(PublishCodeRepository.class)
        public PublishCodeRepository publishCodeRepository(){
            return new MemoryPublishCodeRepository();
        }

        @Bean
        @ConditionalOnMissingBean(UserScopeRepository.class)
        public UserScopeRepository userScopeRepository(){
            return new MemoryUserScopeRepository();
        }
    }

    /**
     * 流媒体过滤器配置
     * @since 0.1
     * @author heartape
     */
    @Configuration
    protected static class MediaFilterRegisterConfigurer {

        @Bean
        @ConditionalOnProperty(name = "spring.live.type", havingValue = "srs")
        public MediaFilterChain srsFilterChain(MediaHookFacade mediaHookFacade){

            ConvertedResponseHttpMessageConverter convertedResponseHttpMessageConverter = new ConvertedResponseHttpMessageConverter();
            SrsPlayEndpointFilter srsPlayEndpointFilters = new SrsPlayEndpointFilter(convertedResponseHttpMessageConverter, mediaHookFacade);
            SrsPublishEndpointFilter srsPublishEndpointFilter = new SrsPublishEndpointFilter(convertedResponseHttpMessageConverter, mediaHookFacade);

            List<Filter> filters = new ArrayList<>();
            filters.add(srsPlayEndpointFilters);
            filters.add(srsPublishEndpointFilter);

            PrefixRequestMatcher prefixRequestMatcher = new PrefixRequestMatcher("/live/hook");
            return new DefaultMediaFilterChain(prefixRequestMatcher, filters);
        }

        @Bean
        @ConditionalOnProperty(name = "spring.live.type", havingValue = "zlm")
        public MediaFilterChain zlmFilterChain(MediaHookFacade mediaHookFacade){

            ConvertedResponseHttpMessageConverter mediaResponseHttpMessageConverter = new ConvertedResponseHttpMessageConverter();
            ZlmPlayEndpointFilter zlmPlayEndpointFilter = new ZlmPlayEndpointFilter(mediaResponseHttpMessageConverter, mediaHookFacade);
            ZlmPublishEndpointFilter zlmPublishEndpointFilter = new ZlmPublishEndpointFilter(mediaResponseHttpMessageConverter, mediaHookFacade);

            List<Filter> filters = new ArrayList<>();
            filters.add(zlmPlayEndpointFilter);
            filters.add(zlmPublishEndpointFilter);

            PrefixRequestMatcher prefixRequestMatcher = new PrefixRequestMatcher("/live/hook");
            return new DefaultMediaFilterChain(prefixRequestMatcher, filters);
        }

        @Bean
        @ConditionalOnMissingBean(MediaHookFacade.class)
        public MediaHookFacade mediaHookAccessor(PublishCodeRepository publishCodeRepository, UserScopeRepository userScopeRepository, MediaProperties mediaProperties){
            return new RepositoryMediaHookFacade(publishCodeRepository, userScopeRepository, mediaProperties.getProtocol(), mediaProperties.getHost(), mediaProperties.getApp());
        }
    }

}
