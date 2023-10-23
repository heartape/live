package com.heartape.live.ums.user;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户注册信息
 */
@Getter
@Setter
public class RegisterForm {

    private String username;
    private String password;
    private String email;
    private String emailCode;
    private String phone;
    private String phoneCode;

    private String code;
    private String codeId;

}
