package com.heartape.live.im.interceptor;

import com.heartape.live.im.context.Context;
import com.heartape.live.im.send.Send;

import java.util.function.Supplier;

public abstract class AbstractInterceptorIterator<T extends Context> implements InterceptorIterator<T> {

    /**
     * 获取拦截器数量
     * @return 拦截器数量
     */
    protected abstract int size();

    /**
     * 获取指定索引拦截器
     * @param index 索引
     * @return 指定索引拦截器
     */
    protected abstract Interceptor<T> interceptor(int index);

    @Override
    public Send next(T context, Supplier<Send> joinPoint) {
        return next(context, 0, joinPoint);
    }

    protected Send next(T context, int index, Supplier<Send> joinPoint) {
        if (size() <= index){
            return joinPoint.get();
        }
        Interceptor<T> interceptor = interceptor(index);
        Send preSend = interceptor.preSend(context);
        if (preSend != null){
            return preSend;
        }
        Send send = next(context, index + 1, joinPoint);
        return interceptor.afterSend(context, send);
    }

}
