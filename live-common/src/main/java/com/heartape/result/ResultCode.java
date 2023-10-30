package com.heartape.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {
    SUCCESS(0, "success"),
    SYSTEM_INNER_ERROR(1000, "system inner error"),
    PERMISSION_DENIED(4000, "permission denied"),
    ;

    private final int code;
    private final String message;
}
