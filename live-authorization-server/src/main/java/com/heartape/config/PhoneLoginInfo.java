package com.heartape.config;

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
public class PhoneLoginInfo {

    private String phone;
    private String code;
    private UserStatus status;
    private Set<String> authorities;

}
