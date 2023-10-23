package com.heartape.live.ums.user;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户注册信息
 */
@Getter
@Setter
public class PhoneRegisterForm {

    /** 用户名 */
    private String username;
    /** 密码 */
    private String password;
    /** 手机号 */
    private String phone;
    /** 手机验证码 */
    private String phoneCode;
    /** 验证码 */
    private String code;

}
