package com.heartape.live.ums.user;


public interface VerificationCode {

    /**
     * @return id
     */
    String getId();

    /**
     * @return 验证码文字
     */
    String getText();

    /**
     * 获取过期时间, 单位: 秒
     * @return 过期时间
     */
    long getExpireTime();
}
