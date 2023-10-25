package com.heartape.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * ums服务调用
 */
@FeignClient(name = "ums", url = "${live.oauth.login.server}", fallbackFactory = RpcUserServiceFallbackFactory.class)
public interface RpcUserService {

    /**
     * 通过username获取 {@link User}
     */
    @PostMapping(value = "${live.oauth.login.username}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    User loadUserByUsername(@RequestBody String username);

    /**
     * 通过phone获取 {@link User}
     */
    @PostMapping(value = "${live.oauth.login.phone}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    User loadUserByPhone(@RequestBody String phone);

}
