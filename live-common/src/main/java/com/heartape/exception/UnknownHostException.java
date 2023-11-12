package com.heartape.exception;

import com.heartape.result.ResultCode;

public class UnknownHostException extends BaseException {
    private final static ResultCode resultCode = ResultCode.UNKNOWN_HOST;

    public UnknownHostException() {
        super(resultCode);
    }
}
