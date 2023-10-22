package com.heartape.live.bullet.exception;

import java.io.Serial;

public class MethodNotSupportException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 4902280943310768669L;

    public MethodNotSupportException(String message) {
        super(message);
    }
}
