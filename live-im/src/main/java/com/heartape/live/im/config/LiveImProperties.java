package com.heartape.live.im.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

/**
 * 配置
 * @since 0.0.1
 * @author heartape
 */
@Getter
@Validated
@ConfigurationProperties(prefix = "live.im")
public class LiveImProperties {

    @Setter
    private String networkInterfaceName;
    private final Cluster cluster;

    @ConstructorBinding
    public LiveImProperties(String networkInterfaceName, Cluster cluster) {
        this.networkInterfaceName = networkInterfaceName;
        this.cluster = Objects.requireNonNullElseGet(cluster, () -> new Cluster(null));
    }

    @Getter
    @AllArgsConstructor
    public static class Cluster {

        @Setter
        private String host;

    }
}
