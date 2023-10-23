package com.heartape.exception;

import com.heartape.result.ResultCode;

/**
 * 系统内部异常
 */
public class SystemInnerException extends BaseException {

    private final static ResultCode resultCode = ResultCode.SYSTEM_INNER_ERROR;

    public SystemInnerException() {
        super(resultCode);
    }
}
