package com.heartape.live.ums.user;


/**
 * 验证码管理基类
 */
public interface VerificationCodeManager {

    /**
     * 保存验证码
     */
    void save(ImageVerificationCode imageVerificationCode);

    /**
     * 校验验证码
     */
    boolean check(String text);

}
