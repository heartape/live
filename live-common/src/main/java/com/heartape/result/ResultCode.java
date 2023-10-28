package com.heartape.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {
    SUCCESS(0, "success"),
    SYSTEM_INNER_ERROR(1000, "system inner error"),
    ;

    private final int code;
    private final String message;
}
