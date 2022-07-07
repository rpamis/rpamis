package com.benym.rpas.common.dto.exception;

import com.benym.rpas.common.dto.enums.ResponseCode;

/**
 * @Time : 2022/7/7 22:37
 */
public class BizException extends AbstractException {

    private static final long serialVersionUID = 1L;

    private static final ResponseCode DEAULT_BIZ_ERRCODE = ResponseCode.BIZ_EXCEPTION_CODE;

    public BizException(String errMessage) {
        super(DEAULT_BIZ_ERRCODE.getCode(), errMessage);
    }

    public BizException(int errCode, String errMessage) {
        super(errCode, errMessage);
    }

    public BizException(String errMessage, Throwable e) {
        super(DEAULT_BIZ_ERRCODE.getCode(), errMessage, e);
    }

    public BizException(int errCode, String errMessage, Throwable e) {
        super(errCode, errMessage, e);
    }
}
