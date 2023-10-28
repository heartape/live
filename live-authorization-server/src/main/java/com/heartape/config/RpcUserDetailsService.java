package com.heartape.config;

import com.heartape.user.UserStatus;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 通过rpc调用来获取{@link UserDetails}
 * @see UserDetailsService
 */
@AllArgsConstructor
public class RpcUserDetailsService implements UserDetailsService {

    /**
     * ums服务调用
     */
    private final RpcUserService rpcUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserLoginInfo userLoginInfo = rpcUserService.loadUserByUsername(username);
        if (userLoginInfo == null) {
            throw new UsernameNotFoundException("not found user");
        }
        UserStatus status = userLoginInfo.getStatus();
        List<SimpleGrantedAuthority> grantedAuthorities = userLoginInfo.getAuthorities()
                .stream()
                .filter(StringUtils::hasText)
                .map(SimpleGrantedAuthority::new)
                .toList();
        return new User(userLoginInfo.getUsername(), userLoginInfo.getPassword(), status == UserStatus.NORMAL, status != UserStatus.DISABLED, true, status != UserStatus.LOCKED, grantedAuthorities);
    }
}
