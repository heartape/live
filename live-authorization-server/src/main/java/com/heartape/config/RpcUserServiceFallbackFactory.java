package com.heartape.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RpcUserServiceFallbackFactory implements FallbackFactory<RpcUserService> {
    @Override
    public RpcUserService create(Throwable cause) {
        return new RpcUserService() {
            @Override
            public User loadUserByUsername(String username) {
                log.error("调用远程服务失败");
                return null;
            }

            @Override
            public User loadUserByPhone(String phone) {
                log.error("调用远程服务失败");
                return null;
            }
        };
    }
}
