package com.benym.rpas.common.dto.exception;

import com.benym.rpas.common.dto.enums.StatusCode;

/**
 * 定义抽象异常类
 * This class is empowered by com.alibaba.cola
 *
 * @Time : 2022/7/7 22:20
 */
public abstract class AbstractException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String errCode;

    private String errMessage;

    private String detailMessage;

    public AbstractException() {
        super();
    }

    public AbstractException(String detailMessage) {
        super(detailMessage);
    }

    public AbstractException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public AbstractException(String errCode, String errMessage, String detailMessage) {
        super(errMessage);
        this.setErrCode(errCode);
        this.setErrMessage(errMessage);
        this.setDetailMessage(detailMessage);
    }

    public AbstractException(String errCode, String errMessage) {
        this(errCode, errMessage, null);
    }

    public AbstractException(StatusCode statusCode) {
        this(statusCode.getCode(), statusCode.getMessage(), null);
    }

    public AbstractException(StatusCode statusCode, String detailMessage) {
        this(statusCode.getCode(), statusCode.getMessage(), detailMessage);
    }

    public AbstractException(StatusCode statusCode, Throwable throwable) {
        super(statusCode.getMessage(), throwable);
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }

    public String getDetailMessage() {
        return detailMessage;
    }

    public void setDetailMessage(String detailMessage) {
        this.detailMessage = detailMessage;
    }
}
