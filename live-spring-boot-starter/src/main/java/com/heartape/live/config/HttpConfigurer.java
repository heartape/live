package com.heartape.live.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

/**
 * 流媒体过滤器http配置
 * @since 0.1
 * @author heartape
 */
@Configuration
public class HttpConfigurer {

    /**
     * 开启负载均衡，需要负载均衡组件支持
     */
    @Bean
    @ConditionalOnProperty(name = "spring.live.loadbalancer", havingValue = "true")
    @LoadBalanced
    public RestOperations loadBalancedRestOperations(){
        return new RestTemplate();
    }

    /**
     * 不开启负载均衡
     */
    @Bean
    @ConditionalOnMissingBean(RestOperations.class)
    public RestOperations restOperations(){
        return new RestTemplate();
    }

}
