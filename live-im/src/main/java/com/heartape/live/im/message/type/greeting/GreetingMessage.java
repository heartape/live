package com.heartape.live.im.message.type.greeting;

import com.heartape.live.im.message.base.BaseMessage;
import com.heartape.live.im.message.type.text.Text;

import java.time.LocalDateTime;

/**
 * 文字
 * @see BaseMessage
 * @see Text
 * @since 0.0.1
 * @author heartape
 */
public class GreetingMessage extends BaseMessage<Greeting> {

    public GreetingMessage(String id, String uid, String purpose, String purposeType, String type, LocalDateTime timestamp, Greeting content) {
        super(id, uid, purpose, purposeType, type, 0, timestamp, content);
    }
}
