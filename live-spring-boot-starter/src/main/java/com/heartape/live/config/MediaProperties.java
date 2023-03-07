package com.heartape.live.config;

import com.heartape.live.MediaServerType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 配置
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "spring.live")
public class MediaProperties {
    /**
     * 在调用其他服务时开启负载均衡
     */
    private Boolean loadbalancer;
    private MediaServerType type;
    /**
     * 允许的协议
     */
    private String protocol;
    /**
     * 流媒体推流ip
     */
    private String host;
    /**
     * 流媒体推流路径
     */
    private String app;
}
