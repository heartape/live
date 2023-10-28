package com.heartape.live.ums.user;


/**
 * 验证码工厂基类
 */
public interface VerificationCodeFactory {

    /**
     * 获取一个验证码
     * @return VerificationCode
     */
    VerificationCode next();

    /**
     * 获取一个验证码,并指定验证码的tag，用于区分验证码
     * @return VerificationCode
     */
    VerificationCode next(String tag);

}
