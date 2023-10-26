package com.heartape.config;

import com.heartape.sms.SmsAuthenticationFilter;
import com.heartape.sms.SmsCodeAuthenticationProvider;
import com.heartape.sms.SmsUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * @return A Spring Security filter chain for authentication.
     */
    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http,
                                                          RpcUserService rpcUserService) throws Exception {
        SmsAuthenticationFilter smsAuthenticationFilter = new SmsAuthenticationFilter(
                new ProviderManager(
                        new SmsCodeAuthenticationProvider(
                                new SmsUserDetailsService(rpcUserService))));
        http
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable)
                // Form login handles the redirect to the login page from the
                // authorization server filter chain
                // .httpBasic(Customizer.withDefaults())
                // .formLogin(Customizer.withDefaults())
                .formLogin(customizer -> customizer.successForwardUrl("/"))
                // .logout(customizer -> customizer.logoutSuccessUrl("/"))
                .addFilterAfter(smsAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

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
}
