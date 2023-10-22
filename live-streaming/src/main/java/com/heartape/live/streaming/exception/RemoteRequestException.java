package com.heartape.live.streaming.exception;

/**
 * 收到的远程请求错误
 */
public class RemoteRequestException extends RuntimeException {

    public RemoteRequestException(String message) {
        super(message);
    }
}
