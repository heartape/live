package com.heartape.sms;

import com.heartape.config.PhoneLoginInfo;
import com.heartape.config.RpcUserService;
import com.heartape.user.UserStatus;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 通过rpc调用来获取{@link UserDetails}
 * @see UserDetailsService
 */
@AllArgsConstructor
public class SmsUserDetailsService implements UserDetailsService {

    /**
     * ums服务调用
     */
    private final RpcUserService rpcUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        PhoneLoginInfo phoneLoginInfo = rpcUserService.loadUserByPhone(username);
        if (phoneLoginInfo == null) {
            throw new UsernameNotFoundException("not found user");
        }
        UserStatus status = phoneLoginInfo.getStatus();
        List<SimpleGrantedAuthority> grantedAuthorities = phoneLoginInfo.getAuthorities()
                .stream()
                .filter(StringUtils::hasText)
                .map(SimpleGrantedAuthority::new)
                .toList();
        return new User(phoneLoginInfo.getPhone(), phoneLoginInfo.getCode(), status == UserStatus.NORMAL, status != UserStatus.DISABLED, true, status != UserStatus.LOCKED, grantedAuthorities);
    }
}
