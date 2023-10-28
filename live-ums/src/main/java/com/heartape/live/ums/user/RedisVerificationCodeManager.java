package com.heartape.live.ums.user;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 通过redis管理验证码
 */
@AllArgsConstructor
public class RedisVerificationCodeManager implements VerificationCodeManager {

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void save(VerificationCode verificationCode) {
        if (StringUtils.hasText(verificationCode.getTag())){
            Map<String, String> verificationCodeMap = Map.of("tag", verificationCode.getTag(), "text", verificationCode.getText());
            redisTemplate.opsForHash().putAll(verificationCode.getId(), verificationCodeMap);
            redisTemplate.expire(verificationCode.getId(), verificationCode.getExpireTime(), TimeUnit.SECONDS);
        } else {
            redisTemplate.opsForValue().set(verificationCode.getId(), verificationCode.getText(), verificationCode.getExpireTime(), TimeUnit.SECONDS);
        }
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
