package com.heartape.live.im.message.type.video;

import com.heartape.live.im.message.base.BaseMessage;
import com.heartape.live.im.message.base.ContentMessage;

/**
 * 视频
 * @see ContentMessage
 * @see Video
 * @since 0.0.1
 * @author heartape
 */
public class VideoMessage extends ContentMessage<Video> {
    public VideoMessage(BaseMessage baseMessage, Video content) {
        super(baseMessage, content);
    }
}
