package com.heartape.live.im.gateway.proxy;

import com.heartape.live.im.context.MessageContext;
import com.heartape.live.im.context.PromptContext;
import com.heartape.live.im.gateway.Gateway;
import com.heartape.live.im.interceptor.InterceptorRegister;
import com.heartape.live.im.send.Send;
import lombok.AllArgsConstructor;

/**
 * 消息网关静态代理类
 * @see Gateway
 * @since 0.0.1
 * @author heartape
 */
@AllArgsConstructor
public class GatewayStaticProxy implements Gateway {

    private final Gateway gateway;

    private final InterceptorRegister<MessageContext> messageInterceptorRegister;

    private final InterceptorRegister<PromptContext> systemInterceptorRegister;

    @Override
    public Send message(MessageContext messageContext) {
        return this.messageInterceptorRegister.intercept(messageContext, () -> this.gateway.message(messageContext));
    }

    @Override
    public Send prompt(PromptContext promptContext) {
        return this.systemInterceptorRegister.intercept(promptContext, () -> this.gateway.prompt(promptContext));
    }
}
