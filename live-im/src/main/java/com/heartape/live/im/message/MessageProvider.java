package com.heartape.live.im.message;

import com.heartape.live.im.context.MessageContext;
import com.heartape.live.im.send.Send;

/**
 * 提供消息处理
 * @since 0.0.1
 * @author heartape
 */
public interface MessageProvider {

    /**
     * 消息处理
     * @param messageContext 客户端消息
     * @return 服务器消息
     * @see Message
     * @see Send
     */
    Send process(MessageContext messageContext);

}
