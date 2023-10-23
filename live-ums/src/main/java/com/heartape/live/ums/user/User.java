package com.heartape.live.ums.user;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 用户
 */
@Getter
@Setter
public class User {

    /** id 主键*/
    private String id;
    private String avatar;
    private String username;
    private String nickname;
    private String password;
    private String phone;
    private String email;

    private String isEmailVerified;
    private String isPhoneVerified;

    private UserStatus status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

}
