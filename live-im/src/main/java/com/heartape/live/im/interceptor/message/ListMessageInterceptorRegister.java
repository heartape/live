package com.heartape.live.im.interceptor.message;

import com.heartape.live.im.context.MessageContext;
import com.heartape.live.im.interceptor.AbstractInterceptorIterator;
import com.heartape.live.im.interceptor.Interceptor;
import com.heartape.live.im.interceptor.InterceptorRegister;
import com.heartape.live.im.send.Send;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * 默认拦截器注册
 * @see InterceptorRegister
 * @since 0.0.1
 * @author heartape
 */
public class ListMessageInterceptorRegister extends AbstractInterceptorIterator<MessageContext> implements MessageInterceptorRegister {

    private final List<Interceptor<MessageContext>> interceptorList;

    public ListMessageInterceptorRegister() {
        this.interceptorList = new ArrayList<>();
    }

    @Override
    public InterceptorRegister<MessageContext> register(Interceptor<MessageContext> interceptor) {
        this.interceptorList.add(interceptor);
        return this;
    }

    @Override
    public Send intercept(MessageContext messageContext, Supplier<Send> joinPoint) {
        return next(messageContext, joinPoint);
    }

    @Override
    public int size() {
        return this.interceptorList.size();
    }

    @Override
    public Interceptor<MessageContext> interceptor(int index) {
        return this.interceptorList.get(index);
    }
}
