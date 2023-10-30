package com.heartape.live.im.message;

/**
 * 消息
 * @since 0.0.1
 * @author heartape
 */
public interface Message {

    /**
     * get id
     */
    String getId();

    /**
     * get uid
     */
    String getUid();

    /**
     * get Purpose id
     */
    String getPurpose();

    String getPurposeType();

    /**
     * @see MessageType
     */
    String getType();

    /**
     * get Content
     */
    Object getContent();

}
