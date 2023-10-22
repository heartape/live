package com.heartape.live.im.message.base;

import lombok.Getter;

/**
 * 包含内容的消息
 * @param <T> 内容类型
 * @since 0.0.1
 * @author heartape
 */
@Getter
public class ContentMessage<T extends Content> extends BaseMessage {

    protected T content;

    public ContentMessage(BaseMessage baseMessage, T content) {
        super();
        this.uid = baseMessage.uid;
        this.purpose = baseMessage.purpose;
        this.purposeType = baseMessage.purposeType;
        this.type = baseMessage.type;
        this.timestamp = baseMessage.timestamp;
        this.content = content;
    }
}
