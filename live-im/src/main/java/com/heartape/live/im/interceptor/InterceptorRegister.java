package com.heartape.live.im.interceptor;

import com.heartape.live.im.context.Context;
import com.heartape.live.im.send.Send;

import java.util.function.Supplier;

/**
 * 拦截器注册基类
 * @see Interceptor
 * @since 0.0.1
 * @author heartape
 */
public interface InterceptorRegister<T extends Context> {

    /**
     * 注册拦截器
     * @param interceptor 拦截器
     * @return InterceptorRegister
     */
    InterceptorRegister<T> register(Interceptor<T> interceptor);

    /**
     * 前置处理
     * @param context 消息上下文
     * @return Send
     */
    Send intercept(T context, Supplier<Send> joinPoint);

}
