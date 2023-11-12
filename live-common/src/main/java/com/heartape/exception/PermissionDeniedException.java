package com.heartape.exception;

import com.heartape.result.ResultCode;

public class PermissionDeniedException extends BaseException {

    private final static ResultCode resultCode = ResultCode.PERMISSION_DENIED;

    public PermissionDeniedException() {
        super(resultCode);
    }
}
