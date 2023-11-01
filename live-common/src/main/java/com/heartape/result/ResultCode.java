package com.heartape.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {
    SUCCESS(0, "success"),
    SYSTEM_INNER_ERROR(1000, "system inner error"),
    CAN_NOT_FIND_HOST(1001, "can not find host"),
    UNKNOWN_HOST(1002, "unknown host"),
    PERMISSION_DENIED(4000, "permission denied"),

    ;

    private final int code;
    private final String message;
}
