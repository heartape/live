package com.heartape.live.im.message.type.image;

import com.heartape.live.im.message.base.BaseMessage;
import com.heartape.live.im.message.base.ContentMessage;

/**
 * 图片
 * @see ContentMessage
 * @see Image
 * @since 0.0.1
 * @author heartape
 */
public class ImageMessage extends ContentMessage<Image> {
    public ImageMessage(BaseMessage baseMessage, Image content) {
        super(baseMessage, content);
    }
}
