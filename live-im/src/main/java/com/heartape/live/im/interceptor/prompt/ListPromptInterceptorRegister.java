package com.heartape.live.im.interceptor.prompt;

import com.heartape.live.im.context.PromptContext;
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
public class ListPromptInterceptorRegister extends AbstractInterceptorIterator<PromptContext> implements PromptInterceptorRegister {

    private final List<Interceptor<PromptContext>> interceptorList;

    public ListPromptInterceptorRegister() {
        this.interceptorList = new ArrayList<>();
    }

    @Override
    public InterceptorRegister<PromptContext> register(Interceptor<PromptContext> interceptor) {
        this.interceptorList.add(interceptor);
        return this;
    }

    @Override
    public Send intercept(PromptContext context, Supplier<Send> joinPoint) {
        return next(context, joinPoint);
    }

    @Override
    public int size() {
        return this.interceptorList.size();
    }

    @Override
    public Interceptor<PromptContext> interceptor(int index) {
        return this.interceptorList.get(index);
    }
}
