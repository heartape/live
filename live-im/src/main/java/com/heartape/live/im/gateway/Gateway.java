package com.heartape.live.im.gateway;

import com.heartape.live.im.context.MessageContext;
import com.heartape.live.im.context.PromptContext;
import com.heartape.live.im.send.Send;

/**
 * 消息网关
 * @since 0.0.1
 * @author heartape
 */
public interface Gateway {

    /**
     * 用户间消息
     * <br/>
     * 就算是系统主动发出的消息，只要主体双方是用户，都会被当正常消息处理
     * 比如：申请加好友时的问候语，会在对方同意后会自动由系统发送，此时仍当作消息处理
     * @param messageContext 消息上下文
     * @return Send
     */
    Send message(MessageContext messageContext);

    /**
     * 系统提示：如好友申请提示、加入群聊申请提示、群聊解散提示等
     * <br/>
     * 同时，系统消息不会持久化，因为其仅作为一种提示手段
     * @param promptContext 系统提示上下文
     * @return Send
     */
    Send prompt(PromptContext promptContext);

}
