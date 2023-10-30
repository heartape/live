package com.heartape.live.im.send;

import com.heartape.live.im.message.Message;
import lombok.Getter;

/**
 * 服务端错误消息
 * @since 0.0.1
 * @author heartape
 */
@Getter
public class MessageSend implements Send {

    private final String type;

    private final Message message;

    public MessageSend(Message message) {
        this(SendType.MESSAGE, message);
    }

    public MessageSend(String type, Message message) {
        this.type = type;
        this.message = message;
    }
}
