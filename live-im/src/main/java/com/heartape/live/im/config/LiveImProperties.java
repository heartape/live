package com.heartape.live.im.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 配置
 * @since 0.0.1
 * @author heartape
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "live.im")
public class LiveImProperties {

}
