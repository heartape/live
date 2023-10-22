package com.heartape.live.im.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.heartape.live.im.context.MessageContext;

/**
 * 提供消息转换
 * @see Message
 * @since 0.0.1
 * @author heartape
 */
public interface MessageConverter<T extends Message> {

    /**
     * 转换
     * @param messageContext 消息
     * @return Message
     */
    T convert(MessageContext messageContext) throws JsonProcessingException;

}
