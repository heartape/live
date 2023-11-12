package com.heartape.exception;

import com.heartape.result.ResultCode;

public class CanNotFindHostException extends BaseException {
    private final static ResultCode resultCode = ResultCode.CAN_NOT_FIND_HOST;

    public CanNotFindHostException() {
        super(resultCode);
    }
}
