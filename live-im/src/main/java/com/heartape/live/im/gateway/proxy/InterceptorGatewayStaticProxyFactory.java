package com.heartape.live.im.gateway.proxy;

import com.heartape.live.im.context.MessageContext;
import com.heartape.live.im.context.PromptContext;
import com.heartape.live.im.gateway.Gateway;
import com.heartape.live.im.interceptor.InterceptorRegister;
import lombok.AllArgsConstructor;

/**
 * 创建 Interceptor 静态代理工厂
 * @see GatewayProxyFactory
 * @since 0.0.1
 * @author heartape
 */
@AllArgsConstructor
public class InterceptorGatewayStaticProxyFactory implements GatewayProxyFactory {

    private final InterceptorRegister<MessageContext> messageInterceptorRegister;

    private final InterceptorRegister<PromptContext> systemInterceptorRegister;

    public Gateway create(Gateway gateway) {
        return new GatewayStaticProxy(gateway, messageInterceptorRegister, systemInterceptorRegister);
    }

}
