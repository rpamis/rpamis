package com.benym.rpas.common.dto.exception;

import com.benym.rpas.common.dto.enums.ResponseCode;

/**
 * 定义业务异常类，固定状态码
 * This class is empowered by com.alibaba.cola
 *
 * @Time : 2022/7/7 22:37
 */
public class BizException extends AbstractException {

    private static final long serialVersionUID = 1L;

    private static final ResponseCode DEAULT_BIZ_ERRCODE = ResponseCode.BIZ_EXCEPTION_CODE;

    public BizException() {
        super(DEAULT_BIZ_ERRCODE);
    }

    public BizException(String detailMessage) {
        super(DEAULT_BIZ_ERRCODE.getCode(), DEAULT_BIZ_ERRCODE.getMessage(), detailMessage);
    }

    public BizException(String detailMessage, Throwable e) {
        super(detailMessage, e);
    }

    public BizException(String errCode, String errMessage) {
        super(errCode, errMessage);
    }

    public BizException(Throwable e) {
        super(DEAULT_BIZ_ERRCODE, e);
    }
}
