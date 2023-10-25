package com.heartape.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * ums服务调用
 */
@FeignClient(name = "live-ums", path = "${live.oauth.login.path}", fallbackFactory = RpcUserServiceFallbackFactory.class)
public interface RpcUserService {

    /**
     * 通过username获取 {@link User}
     */
    @GetMapping(value = "${live.oauth.login.username}", produces = MediaType.APPLICATION_JSON_VALUE)
    User loadUserByUsername(@RequestParam String username);

    /**
     * 通过phone获取 {@link User}
     */
    @GetMapping(value = "${live.oauth.login.phone}", produces = MediaType.APPLICATION_JSON_VALUE)
    User loadUserByPhone(@RequestParam String phone);

}
