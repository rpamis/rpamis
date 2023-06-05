package cn.rpamis.common.dto.exception;

import cn.rpamis.common.dto.enums.StatusCode;

/**
 * 自定义异常类，接受任意状态码
 *
 * @author benym
 * @date 2022/7/6 21:39
 */
public class RpamisException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 错误代码
     */
    private String errCode;

    /**
     * 错误消息
     */
    private String errMessage;

    /**
     * 详细信息
     */
    private String detailMessage;

    public RpamisException() {
        super();
    }

    public RpamisException(String errCode, String errMessage, String detailMessage) {
        super(errMessage);
        this.errCode = errCode;
        this.errMessage = errMessage;
        this.detailMessage = detailMessage;
    }

    public RpamisException(String errCode, String errMessage) {
        this(errCode, errMessage, null);
    }

    public RpamisException(StatusCode statusCode) {
        this(statusCode.getCode(), statusCode.getMessage(), null);
    }

    public RpamisException(StatusCode statusCode, String detailMessage) {
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
