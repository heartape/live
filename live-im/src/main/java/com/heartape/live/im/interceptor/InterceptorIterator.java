package com.heartape.live.im.interceptor;

import com.heartape.live.im.context.Context;
import com.heartape.live.im.send.Send;

import java.util.function.Supplier;

/**
 * 拦截器迭代器
 * @since 0.0.1
 * @author heartape
 * @param <T> Context
 */
public interface InterceptorIterator<T extends Context> {

    /**
     * 执行下一个拦截器
     * @param context 上下文
     * @param joinPoint 获取拦截器切入点
     * @return 返回结果
     */
    Send next(T context, Supplier<Send> joinPoint);

}
