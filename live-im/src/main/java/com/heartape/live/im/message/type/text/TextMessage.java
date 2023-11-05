package com.heartape.live.im.message.type.text;

import com.heartape.live.im.message.base.BaseMessage;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

/**
 * 文字
 * @see BaseMessage
 * @see Text
 * @since 0.0.1
 * @author heartape
 */
@AllArgsConstructor
public class TextMessage extends BaseMessage<Text> {
    public TextMessage(String id, String uid, String purpose, String purposeType, String type, LocalDateTime timestamp, Text content) {
        super(id, uid, purpose, purposeType, type, 0, timestamp, content);
    }
}
