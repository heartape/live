package com.heartape.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * ums服务调用
 */
@FeignClient("${live.oauth.login.server}")
public interface RpcUserService {

    /**
     * 通过username获取 {@link User}
     */
    @PostMapping(value = "${live.oauth.login.username}", consumes = "application/json", produces = "application/json")
    User loadUserByUsername(@RequestBody String username);

    /**
     * 通过phone获取 {@link User}
     */
    @PostMapping(value = "${live.oauth.login.phone}", consumes = "application/json", produces = "application/json")
    User loadUserByPhone(@RequestBody String phone);

}
