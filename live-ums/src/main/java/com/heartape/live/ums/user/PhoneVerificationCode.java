package com.heartape.live.ums.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 验证码
 */
@Getter
@AllArgsConstructor
public class PhoneVerificationCode implements VerificationCode {

    private String id;
    private String text;
    private String phone;
    private long expireTime;

    @Override
    public String getTag() {
        return phone;
    }
}
