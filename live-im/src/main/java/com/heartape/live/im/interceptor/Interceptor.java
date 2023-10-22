package com.heartape.live.im.interceptor;

import com.heartape.live.im.send.Send;

/**
 * 拦截器基类
 * @since 0.0.1
 * @author heartape
 */
public interface Interceptor<T> {

    /**
     * 前置处理
     * @param context 消息上下文
     * @return Send
     */
    Send preSend(T context);

    /**
     * 后置处理
     * @param send Send
     * @return Send
     */
    Send afterSend(T context, Send send);

}
