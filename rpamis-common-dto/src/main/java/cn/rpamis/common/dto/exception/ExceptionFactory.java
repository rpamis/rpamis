package cn.rpamis.common.dto.exception;

import cn.rpamis.common.dto.enums.StatusCode;

/**
 * 异常工厂
 *
 * @author benym
 * @date 2022/7/7 22:46
 */
public class ExceptionFactory {

    private ExceptionFactory() {
        throw new IllegalStateException("工厂类，禁止实例化");
    }

    public static BizException bizException(String errMessage, Throwable e) {
        return new BizException(errMessage, e);
    }

    public static BizException bizException(Throwable e) {
        return new BizException(e);
    }

    public static BizException bizException(StatusCode statusCode, Throwable e) {
        return new BizException(statusCode, e);
    }

    public static BizNoStackException bizNoStackException(String errMessage) {
        return new BizNoStackException(errMessage);
    }

    public static BizNoStackException bizNoStackException(String errCode, String errMessage) {
        return new BizNoStackException(errCode, errMessage);
    }

    public static BizNoStackException bizNoStackException(StatusCode statusCode) {
        return new BizNoStackException(statusCode);
    }

    public static SysException sysException(String errMessage, Throwable e) {
        return new SysException(errMessage, e);
    }

    public static SysException sysException(Throwable e) {
        return new SysException(e);
    }

    public static SysException sysException(StatusCode statusCode, Throwable e) {
        return new SysException(statusCode, e);
    }

    public static RpasException rpasException() {
        return new RpasException();
    }

    public static RpasException rpasException(String errCode, String errMessage, String detailMessage) {
        return new RpasException(errCode, errMessage, detailMessage);
    }

    public static RpasException rpasException(String errCode, String errMessage) {
        return new RpasException(errCode, errMessage);
    }

    public static RpasException rpasException(StatusCode statusCode) {
        return new RpasException(statusCode);
    }

    public static RpasException rpasException(StatusCode statusCode, String detailMessage) {
        return new RpasException(statusCode, detailMessage);
    }

    public static ValidException validException(String errMessage) {
        return new ValidException(errMessage);
    }

    public static ValidException validException(String errCode, String errMessage) {
        return new ValidException(errCode, errMessage);
    }

    public static ValidException validException(StatusCode statusCode) {
        return new ValidException(statusCode);
    }
}
