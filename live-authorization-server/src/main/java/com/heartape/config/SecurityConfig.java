package com.heartape.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * @return A Spring Security filter chain for authentication.
     */
    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().authenticated()
                )
                // Form login handles the redirect to the login page from the
                // authorization server filter chain
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults());

        return http.build();
    }

    /**
     * 该账户的权限不影响oidc
     * @return An instance of UserDetailsService for retrieving users to authenticate.
     */
    @Bean
    public UserDetailsService userDetailsService(RpcUserService rpcUserService) {
        return new RpcUserDetailsService(rpcUserService);
    }

    @Bean
    public SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig(RpcUserService rpcUserService){
        return new SmsCodeAuthenticationSecurityConfig(new SmsUserDetailsService(rpcUserService));
    }
}
