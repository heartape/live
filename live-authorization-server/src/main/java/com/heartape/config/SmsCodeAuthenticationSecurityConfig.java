package com.heartape.config;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@AllArgsConstructor
public class SmsCodeAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final UserDetailsService userDetailsService;
    // private final AuthenticationSuccessHandler successHandler;
    // private final AuthenticationFailureHandler failureHandler;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        SmsAuthenticationFilter smsCodeAuthenticationFilter = new SmsAuthenticationFilter();

        smsCodeAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        // smsCodeAuthenticationFilter.setAuthenticationSuccessHandler(successHandler);
        // smsCodeAuthenticationFilter.setAuthenticationFailureHandler(failureHandler);
        // 获取验证码提供者
        SmsCodeAuthenticationProvider smsCodeAuthenticationProvider = new SmsCodeAuthenticationProvider(userDetailsService);
        // 将短信验证码校验器注册到 HttpSecurity， 并将短信验证码过滤器添加在 UsernamePasswordAuthenticationFilter 之前
        http.authenticationProvider(smsCodeAuthenticationProvider).addFilterAfter(smsCodeAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
