package com.heartape.live.ums.user;


/**
 * 验证码管理基类
 */
public interface VerificationCodeManager {

    /**
     * 保存验证码
     */
    void save(VerificationCode verificationCode);

    /**
     * 校验验证码
     */
    boolean check(String key, String text);

    /**
     * 获取验证码
     */
    String query(String key);

}
