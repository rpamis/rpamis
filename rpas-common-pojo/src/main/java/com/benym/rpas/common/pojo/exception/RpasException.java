package com.benym.rpas.common.pojo.exception;

import com.benym.rpas.common.pojo.enums.StatusCode;

/**
 * @Time : 2022/7/6 21:39
 */
public class RpasException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private int errCode;

    private String errMessage;

    private String detailMessage;

    public RpasException() {
        super();
    }

    public RpasException(int errCode, String errMessage, String detailMessage) {
        super(errMessage);
        this.setErrCode(errCode);
        this.setErrMessage(errMessage);
        this.setDetailMessage(detailMessage);
    }

    public RpasException(int errCode, String errMessage) {
        this(errCode, errMessage, null);
    }

    public RpasException(StatusCode statusCode) {
        this(statusCode.getCode(), statusCode.getMessage(), null);
    }

    public RpasException(StatusCode statusCode, String detailMessage) {
        this(statusCode.getCode(), statusCode.getMessage(), detailMessage);
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
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
