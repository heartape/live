package com.heartape.live.exception;

/**
 * 权限认证异常
 */
public class PermissionDeniedException extends RuntimeException {
    public PermissionDeniedException() {
        super();
    }

    public PermissionDeniedException(String message) {
        super(message);
    }
}
