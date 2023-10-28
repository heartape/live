package com.heartape.live.ums.user;

import com.heartape.user.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginInfo {

    private String username;
    private String password;
    private UserStatus status;
    private Set<String> authorities;

}
