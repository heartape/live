package com.heartape.live.ums.user;

import java.util.Random;
import java.util.UUID;

/**
 * 手机验证码生成工厂。
 */
public class PhoneVerificationCodeFactory implements VerificationCodeFactory {

    private final Random random = new Random();

    @Override
    public VerificationCode next() {
        return next(null);
    }

    @Override
    public VerificationCode next(String tag) {
        String id = UUID.randomUUID().toString();
        int i = random.nextInt(1000000);
        String text = Integer.toString(i);
        return new PhoneVerificationCode(id, text, tag, 60);
    }
}
