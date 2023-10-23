package com.heartape.live.ums;

import com.heartape.live.ums.user.CachedImageVerificationCodeFactory;
import com.heartape.live.ums.user.RedisVerificationCodeManager;
import com.heartape.live.ums.user.VerificationCodeFactory;
import com.heartape.live.ums.user.VerificationCodeManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class UmsAutoConfiguration {

    @Bean
    public VerificationCodeFactory verificationCodeFactory() {
        return new CachedImageVerificationCodeFactory();
    }

    @Bean
    public VerificationCodeManager verificationCodeManager(RedisTemplate<String, String> redisTemplate) {
        return new RedisVerificationCodeManager(redisTemplate);
    }

}
