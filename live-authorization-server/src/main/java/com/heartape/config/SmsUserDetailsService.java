package com.heartape.config;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

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
        User user = rpcUserService.loadUserByPhone(username);
        if (user == null) {
            throw new UsernameNotFoundException("not found user");
        }
        return user;
    }
}
