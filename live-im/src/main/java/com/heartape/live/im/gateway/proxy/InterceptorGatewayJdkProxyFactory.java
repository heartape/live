package com.heartape.live.im.gateway.proxy;

import com.heartape.live.im.context.MessageContext;
import com.heartape.live.im.context.PromptContext;
import com.heartape.live.im.gateway.Gateway;
import com.heartape.live.im.interceptor.InterceptorRegister;
import lombok.AllArgsConstructor;

import java.lang.reflect.Proxy;

/**
 * 创建 Interceptor jdk动态代理工厂
 * @since 0.0.1
 * @author heartape
 */
@AllArgsConstructor
public class InterceptorGatewayJdkProxyFactory implements GatewayProxyFactory {

    private final InterceptorRegister<MessageContext> messageInterceptorRegister;

    private final InterceptorRegister<PromptContext> systemInterceptorRegister;

    public Gateway create(Gateway gateway) {
        return (Gateway)Proxy.newProxyInstance(
                Gateway.class.getClassLoader(),
                new Class[]{Gateway.class},
                new GatewayJdkProxy(gateway, messageInterceptorRegister, systemInterceptorRegister));
    }

}
