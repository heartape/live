package com.heartape.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 配置文件
 */
@Getter
@Setter
@ConfigurationProperties("live.oauth")
public class Oauth2ServerProperties {

    private Rsa rsa;
    private Login login;

    @Getter
    @Setter
    public static class Rsa {
        private String privateKeyPem;
        private String publicKeyPem;
    }

    @Getter
    @Setter
    public static class Login {
        private String path;
        private String username;
        private String phone;
    }
}
