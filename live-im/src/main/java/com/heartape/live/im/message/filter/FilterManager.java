package com.heartape.live.im.message.filter;

import com.heartape.live.im.message.Message;
import com.heartape.live.im.send.Send;

/**
 * 消息过滤器管理
 * @since 0.0.1
 * @author heartape
 */
public interface FilterManager<T extends Message> {

    /**
     * 注册
     * @param filter 过滤器
     */
    void register(Filter<T> filter);

    /**
     * 执行过滤器
     * @param t Message
     * @return Send
     */
    Send doFilter(T t);

}
