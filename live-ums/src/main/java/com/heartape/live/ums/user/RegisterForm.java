package com.heartape.live.ums.user;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户注册信息
 */
@Getter
@Setter
public class RegisterForm {

    /** 用户名 */
    private String username;
    /** 密码 */
    private String password;
    /** 验证码 */
    private String code;

}
