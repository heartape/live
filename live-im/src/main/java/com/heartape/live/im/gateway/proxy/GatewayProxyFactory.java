package com.heartape.live.im.gateway.proxy;

import com.heartape.live.im.gateway.Gateway;

/**
 * 消息网关代理工厂
 * @since 0.0.1
 * @author heartape
 */
public interface GatewayProxyFactory {

    /**
     * 创建代理类
     * @param gateway 被代理类
     * @return 代理类
     */
    Gateway create(Gateway gateway);

}
