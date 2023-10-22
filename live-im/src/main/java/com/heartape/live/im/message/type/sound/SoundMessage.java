package com.heartape.live.im.message.type.sound;

import com.heartape.live.im.message.base.BaseMessage;
import com.heartape.live.im.message.base.ContentMessage;

/**
 * 语音
 * @see ContentMessage
 * @see Sound
 * @since 0.0.1
 * @author heartape
 */
public class SoundMessage extends ContentMessage<Sound> {
    public SoundMessage(BaseMessage baseMessage, Sound content) {
        super(baseMessage, content);
    }
}
