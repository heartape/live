package com.heartape.live.im.message.type.text;

import com.heartape.live.im.message.base.BaseMessage;
import com.heartape.live.im.message.base.ContentMessage;

/**
 * 文字
 * @see ContentMessage
 * @see Text
 * @since 0.0.1
 * @author heartape
 */
public class TextMessage extends ContentMessage<Text> {
    public TextMessage(BaseMessage baseMessage, Text content) {
        super(baseMessage, content);
    }

}
