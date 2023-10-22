package com.heartape.live.im.message.type.greeting;

import com.heartape.live.im.message.base.BaseMessage;
import com.heartape.live.im.message.base.ContentMessage;
import com.heartape.live.im.message.type.text.Text;

/**
 * 文字
 * @see ContentMessage
 * @see Text
 * @since 0.0.1
 * @author heartape
 */
public class GreetingMessage extends ContentMessage<Greeting> {
    public GreetingMessage(BaseMessage baseMessage, Greeting content) {
        super(baseMessage, content);
    }

}
