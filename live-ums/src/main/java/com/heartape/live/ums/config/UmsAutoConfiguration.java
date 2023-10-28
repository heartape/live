package com.heartape.live.ums.config;

import com.heartape.live.ums.user.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class UmsAutoConfiguration {

    @Bean("imageVerificationCodeFactory")
    public VerificationCodeFactory imageVerificationCodeFactory() {
        return new CachedImageVerificationCodeFactory();
    }

    @Bean("phoneVerificationCodeFactory")
    public VerificationCodeFactory phoneVerificationCodeFactory() {
        return new PhoneVerificationCodeFactory();
    }

    @Bean
    public VerificationCodeManager verificationCodeManager(RedisTemplate<String, String> redisTemplate) {
        return new RedisVerificationCodeManager(redisTemplate);
    }

}
