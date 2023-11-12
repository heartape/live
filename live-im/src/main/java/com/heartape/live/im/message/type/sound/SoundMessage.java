package com.heartape.live.im.message.type.sound;

import com.heartape.live.im.message.base.BaseMessage;

import java.time.LocalDateTime;

/**
 * 语音
 * @see BaseMessage
 * @see Sound
 * @since 0.0.1
 * @author heartape
 */
public class SoundMessage extends BaseMessage<Sound> {
    public SoundMessage(String id, String uid, String purpose, String purposeType, String type, LocalDateTime timestamp, Sound content) {
        super(id, uid, purpose, purposeType, type, 0, timestamp, content);
    }
}
