package com.heartape.live.im.message.type.location;

import com.heartape.live.im.message.base.BaseMessage;

import java.time.LocalDateTime;

/**
 * 位置
 * @see BaseMessage
 * @see Location
 * @since 0.0.1
 * @author heartape
 */
public class LocationMessage extends BaseMessage<Location> {
    public LocationMessage(String id, String uid, String purpose, String purposeType, String type, LocalDateTime timestamp, Location content) {
        super(id, uid, purpose, purposeType, type, 0, timestamp, content);
    }
}
