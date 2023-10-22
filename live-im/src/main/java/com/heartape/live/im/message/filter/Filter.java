package com.heartape.live.im.message.filter;

import com.heartape.live.im.message.Message;
import com.heartape.live.im.send.Send;

/**
 * 消息过滤器
 * @since 0.0.1
 * @author heartape
 */
public interface Filter<T extends Message> {

    Send doFilter(T t);

}
