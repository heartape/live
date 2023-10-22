package com.heartape.live.im.prompt;

import com.heartape.live.im.context.PromptContext;
import com.heartape.live.im.message.Message;
import com.heartape.live.im.send.Send;

/**
 * 提供系统提示处理
 * @since 0.0.1
 * @author heartape
 */
public interface PromptProvider {

    /**
     * 消息处理
     * @param promptContext 系统提示
     * @return 服务器消息
     * @see Message
     * @see Send
     */
    Send process(PromptContext promptContext);

}
