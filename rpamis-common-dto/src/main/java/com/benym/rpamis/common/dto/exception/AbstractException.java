package com.benym.rpamis.common.dto.exception;

import com.benym.rpamis.common.dto.enums.StatusCode;

/**
 * 定义抽象异常类
 * This class is empowered by com.alibaba.cola
 *
 * @author benym
 * @date 2022/7/7 22:20
 */
public abstract class AbstractException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String errCode;

    private String errMessage;

    private Throwable throwable;

    protected AbstractException(String errMessage) {
        super(errMessage);
    }

    protected AbstractException(String errMessage, Throwable throwable) {
        super(errMessage, throwable);
    }


    protected AbstractException(String errCode, String errMessage) {
        this(errCode, errMessage, null);
    }

    protected AbstractException(String errCode, String errMessage, Throwable throwable) {
        super(errMessage);
        this.errCode = errCode;
        this.errMessage = errMessage;
        this.throwable = throwable;
    }

    protected AbstractException(StatusCode statusCode) {
        this(statusCode.getCode(), statusCode.getMessage(), null);
    }

    protected AbstractException(StatusCode statusCode, Throwable throwable) {
        this(statusCode.getCode(), statusCode.getMessage(), throwable);
    }

    public String getErrCode() {
        return errCode;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public Throwable getThrowable() {
        return throwable;
    }
}
