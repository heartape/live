package com.heartape.live.im.message.type.location;

import com.heartape.live.im.message.base.BaseMessage;
import com.heartape.live.im.message.base.ContentMessage;

/**
 * 位置
 * @see ContentMessage
 * @see Location
 * @since 0.0.1
 * @author heartape
 */
public class LocationMessage extends ContentMessage<Location> {
    public LocationMessage(BaseMessage baseMessage, Location content) {
        super(baseMessage, content);
    }
}
