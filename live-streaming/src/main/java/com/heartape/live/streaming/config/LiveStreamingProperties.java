package com.heartape.live.streaming.config;

import com.heartape.live.streaming.filter.Type;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 配置
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "live.streaming")
public class LiveStreamingProperties {

    private Type type;

    private Code code;

    @Getter
    @Setter
    static class Code {

        private String url;

    }

}
