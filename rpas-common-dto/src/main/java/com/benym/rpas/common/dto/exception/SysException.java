package com.benym.rpas.common.dto.exception;

import com.benym.rpas.common.dto.enums.ResponseCode;

/**
 * @Time : 2022/7/7 22:42
 */
public class SysException extends AbstractException {

    private static final long serialVersionUID = 1L;

    private static final ResponseCode DEFAULT_SYS_ERRCODE = ResponseCode.SYS_EXCEPTION_CODE;

    public SysException(String errMessage) {
        super(DEFAULT_SYS_ERRCODE.getCode(), errMessage);
    }

    public SysException(int errCode, String errMessage) {
        super(errCode, errMessage);
    }

    public SysException(String errMessage, Throwable e) {
        super(DEFAULT_SYS_ERRCODE.getCode(), errMessage, e);
    }

    public SysException(int errCode, String errMessage, Throwable e) {
        super(errCode, errMessage, e);
    }
}
