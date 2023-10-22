package com.heartape.live.bullet.exception;

import java.io.Serial;

public class FlowStatusException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 4902280943310768669L;

    public FlowStatusException(String message) {
        super(message);
    }
}
