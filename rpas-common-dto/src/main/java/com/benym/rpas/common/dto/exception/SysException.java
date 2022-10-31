package com.benym.rpas.common.dto.exception;

import com.benym.rpas.common.dto.enums.ResponseCode;

/**
 * 定义系统异常类，固定状态码
 * This class is empowered by com.alibaba.cola
 *
 * @Time : 2022/7/7 22:42
 */
public class SysException extends AbstractException {

    private static final long serialVersionUID = 1L;

    private static final ResponseCode DEFAULT_SYS_ERRCODE = ResponseCode.SYS_EXCEPTION_CODE;

    public SysException() {
        super(DEFAULT_SYS_ERRCODE);
    }

    public SysException(String errMessage, Throwable e) {
        super(DEFAULT_SYS_ERRCODE.getCode(), errMessage, e);
    }

    public SysException(String errMessage) {
        super(DEFAULT_SYS_ERRCODE.getCode(), errMessage);
    }

    public SysException(String errCode, String errMessage) {
        super(errCode, errMessage);
    }

    public SysException(String errCode, String errMessage, Throwable e) {
        super(errCode, errMessage, e);
    }

    public SysException(Throwable e) {
        super(DEFAULT_SYS_ERRCODE, e);
    }
}
