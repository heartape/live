package com.heartape.live.ums.user;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 通过redis管理验证码
 */
@AllArgsConstructor
public class RedisVerificationCodeManager implements VerificationCodeManager {

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void save(ImageVerificationCode imageVerificationCode) {
        redisTemplate.opsForValue().set(imageVerificationCode.getId(), imageVerificationCode.getText(), imageVerificationCode.getExpireTime(), TimeUnit.SECONDS);
    }

    @Override
    public boolean check(String id, String text) {
        String textStored = redisTemplate.opsForValue().get(id);
        return Objects.equals(textStored, text);
    }

    @Override
    public String query(String id) {
        return redisTemplate.opsForValue().get(id);
    }
}
