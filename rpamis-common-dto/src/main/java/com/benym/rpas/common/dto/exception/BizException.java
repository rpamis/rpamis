package com.benym.rpas.common.dto.exception;

import com.benym.rpas.common.dto.enums.ResponseCode;

/**
 * 定义业务异常类，固定状态码
 * This class is empowered by com.alibaba.cola
 *
 * @author benym
 * @date 2022/7/7 22:37
 */
public class BizException extends AbstractException {

    private static final long serialVersionUID = 1L;

    private static final ResponseCode DEAULT_BIZ_ERRCODE = ResponseCode.BIZ_EXCEPTION_CODE;

    public BizException() {
        super(DEAULT_BIZ_ERRCODE);
    }

    public BizException(String errMessage) {
        super(DEAULT_BIZ_ERRCODE.getCode(), errMessage);
    }

    public BizException(String errMessage, Throwable e) {
        super(DEAULT_BIZ_ERRCODE.getCode(), errMessage, e);
    }

    public BizException(String errCode, String errMessage) {
        super(errCode, errMessage);
    }

    public BizException(String errCode, String errMessage, Throwable e) {
        super(errCode, errMessage, e);
    }

    public BizException(Throwable e) {
        super(DEAULT_BIZ_ERRCODE, e);
    }
}
