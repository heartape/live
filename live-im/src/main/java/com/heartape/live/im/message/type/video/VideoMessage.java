package com.heartape.live.im.message.type.video;

import com.heartape.live.im.message.base.BaseMessage;

import java.time.LocalDateTime;

/**
 * 视频
 * @see BaseMessage
 * @see Video
 * @since 0.0.1
 * @author heartape
 */
public class VideoMessage extends BaseMessage<Video> {
    public VideoMessage(String id, String uid, String purpose, String purposeType, String type, LocalDateTime timestamp, Video content) {
        super(id, uid, purpose, purposeType, type, 0, timestamp, content);
    }
}
