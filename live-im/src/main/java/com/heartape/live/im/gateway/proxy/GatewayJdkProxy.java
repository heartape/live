package com.heartape.live.im.gateway.proxy;

import com.heartape.live.im.context.MessageContext;
import com.heartape.live.im.context.PromptContext;
import com.heartape.live.im.gateway.Gateway;
import com.heartape.live.im.interceptor.InterceptorRegister;
import com.heartape.live.im.send.Send;
import lombok.AllArgsConstructor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * jdk动态代理类
 * @since 0.0.1
 * @author heartape
 */
@AllArgsConstructor
public class GatewayJdkProxy implements InvocationHandler {

    private final Gateway gateway;

    private final InterceptorRegister<MessageContext> messageInterceptorRegister;

    private final InterceptorRegister<PromptContext> systemInterceptorRegister;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        if (method.getName().equals("message")){
            MessageContext messageContext = (MessageContext) args[0];
            return this.messageInterceptorRegister.intercept(messageContext, () -> {
                try {
                    return (Send)method.invoke(this.gateway, args);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            });
        } else if (method.getName().equals("prompt")){
            PromptContext promptContext = (PromptContext) args[0];
            return this.systemInterceptorRegister.intercept(promptContext, () -> {
                try {
                    return (Send)method.invoke(this.gateway, args);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            });
        } else {
            throw new RuntimeException();
        }
    }
}
