package com.heartape.exception;

import com.heartape.result.ResultCode;
import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

    protected final int code;
    protected final String message;

    public BaseException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
    }

}
