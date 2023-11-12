package com.heartape.live.im.message.type.image;

import com.heartape.live.im.message.base.BaseMessage;

import java.time.LocalDateTime;

/**
 * 图片
 * @see BaseMessage
 * @see Image
 * @since 0.0.1
 * @author heartape
 */
public class ImageMessage extends BaseMessage<Image> {
    public ImageMessage(String id, String uid, String purpose, String purposeType, String type, LocalDateTime timestamp, Image content) {
        super(id, uid, purpose, purposeType, type, 0, timestamp, content);
    }
}
