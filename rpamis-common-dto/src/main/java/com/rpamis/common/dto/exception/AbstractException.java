package com.rpamis.common.dto.exception;

import com.rpamis.common.dto.enums.StatusCode;

/**
 * 定义抽象异常类
 * This class is empowered by com.alibaba.cola
 *
 * @author benym
 * @date 2022/7/7 22:20
 */
public abstract class AbstractException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 错误代码
     */
    private String errCode;

    /**
     * 错误消息
     */
    private String errMessage;

    protected AbstractException(String errMessage) {
        super(errMessage);
    }

    protected AbstractException(String errMessage, Throwable throwable) {
        super(errMessage, throwable);
    }

    protected AbstractException(String errCode, String errMessage) {
        super(errMessage);
        this.setErrCode(errCode);
        this.setErrMessage(errMessage);
    }

    protected AbstractException(String errCode, String errMessage, Throwable throwable) {
        super(errMessage, throwable);
        this.setErrCode(errCode);
        this.setErrMessage(errMessage);
    }

    protected AbstractException(StatusCode statusCode) {
        this(statusCode.getCode(), statusCode.getMessage());
    }

    protected AbstractException(StatusCode statusCode, Throwable throwable) {
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
}
