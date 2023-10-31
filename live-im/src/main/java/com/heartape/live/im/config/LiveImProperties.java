package com.heartape.live.im.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;
import java.util.Set;

/**
 * 配置
 * @since 0.0.1
 * @author heartape
 */
@Getter
@Validated
@ConfigurationProperties(prefix = "live.im")
public class LiveImProperties {

    private final Cluster cluster;

    @ConstructorBinding
    public LiveImProperties(Cluster cluster) {
        this.cluster = Objects.requireNonNullElseGet(cluster, () -> new Cluster(false, null));
    }

    @Getter
    public static class Cluster {

        private final boolean enabled;
        @Setter
        private Set<String> servers;

        @ConstructorBinding
        public Cluster(@DefaultValue("false") boolean enabled, Set<String> servers) {
            this.enabled = enabled;
            this.servers = Objects.requireNonNullElseGet(servers, Set::of);
        }
    }
}
