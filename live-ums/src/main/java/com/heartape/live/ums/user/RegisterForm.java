package com.heartape.live.ums.user;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

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
    /** 验证码id */
    private String id;
    /** 验证码 */
    private String code;

    public User toUser() {
        LocalDateTime now = LocalDateTime.now();
        return new User(null, null, username, password, UUID.randomUUID().toString(), null, null, false, false, UserStatus.NORMAL, now, now);
    }

}
