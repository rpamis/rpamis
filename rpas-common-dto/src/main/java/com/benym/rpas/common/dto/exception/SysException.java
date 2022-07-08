package com.benym.rpas.common.dto.exception;

import com.benym.rpas.common.dto.enums.ResponseCode;

/**
 * 定义系统异常类，固定状态码
 *
 * @Time : 2022/7/7 22:42
 */
public class SysException extends AbstractException {

    private static final long serialVersionUID = 1L;

    private static final ResponseCode DEFAULT_SYS_ERRCODE = ResponseCode.SYS_EXCEPTION_CODE;

    public SysException() {
        super(DEFAULT_SYS_ERRCODE);
    }

    public SysException(String detailMessage) {
        super(DEFAULT_SYS_ERRCODE.getCode(), DEFAULT_SYS_ERRCODE.getMessage(), detailMessage);
    }

    public SysException(String detailMessage, Throwable e) {
        super(detailMessage, e);
    }

    public SysException(int errCode, String errMessage) {
        super(errCode, errMessage);
    }

    public SysException(Throwable e) {
        super(DEFAULT_SYS_ERRCODE, e);
    }
}
