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

}
