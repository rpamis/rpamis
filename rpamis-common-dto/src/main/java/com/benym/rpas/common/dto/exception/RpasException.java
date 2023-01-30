package com.benym.rpas.common.dto.exception;

import com.benym.rpas.common.dto.enums.StatusCode;

/**
 * 自定义异常类，接受任意状态码
 *
 * @author benym
 * @date 2022/7/6 21:39
 */
public class RpasException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String errCode;

    private String errMessage;

    private String detailMessage;

    public RpasException() {
        super();
    }

    public RpasException(String errCode, String errMessage, String detailMessage) {
        super(errMessage);
        this.errCode = errCode;
        this.errMessage = errMessage;
        this.detailMessage = detailMessage;
    }

    public RpasException(String errCode, String errMessage) {
        this(errCode, errMessage, null);
    }

    public RpasException(StatusCode statusCode) {
        this(statusCode.getCode(), statusCode.getMessage(), null);
    }

    public RpasException(StatusCode statusCode, String detailMessage) {
        this(statusCode.getCode(), statusCode.getMessage(), detailMessage);
    }

    public String getErrCode() {
        return errCode;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public String getDetailMessage() {
        return detailMessage;
    }

}
