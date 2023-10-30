package com.heartape.live.im.send;

import lombok.Getter;

/**
 * 服务端错误消息
 * @since 0.0.1
 * @author heartape
 */
@Getter
public class ErrorSend implements Send {

    private final String type;

    private final String reason;

    public ErrorSend(String reason) {
        this(SendType.ERROR, reason);
    }

    public ErrorSend(String type, String reason) {
        this.type = type;
        this.reason = reason;
    }
}
