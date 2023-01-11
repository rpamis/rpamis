package com.benym.rpas.common.dto.enums;

/**
 * @author benym
 * @date 2022/7/6 21:18
 */
public enum ResponseCode implements StatusCode {

    /**
     * 请求成功
     */
    SUCCESS("Success", "请求成功"),
    /**
     * 请求失败
     */
    FAILED("Failed", "请求失败"),
    /**
     * 业务异常
     */
    BIZ_EXCEPTION_CODE("BizException", "业务异常"),
    /**
     * 系统异常
     */
    SYS_EXCEPTION_CODE("SysException", "系统异常"),
    /**
     * 系统内部异常
     */
    RPAS_EXCEPTION_CODE("InternalException", "系统内部异常"),
    /**
     * 未知异常
     */
    UNKNOWN_EXCEPTION_CODE("UnknownException", "未知异常"),
    /**
     * 参数不符合预期
     */
    VALID_EXCEPTION_CODE("ValidException", "参数不符合预期"),
    /**
     * 参数校验失败
     */
    VALIDATE_ERROR("ValidateError", "参数校验失败"),
    /**
     * Json格式不正确
     */
    READ_JSON_ERROR("ReadJsonError", "Json格式不正确"),
    /**
     * 参数错误
     */
    INVALID_PARAMETER("InvalidParameter", "参数错误");

    private final String code;
    private final String message;

    ResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
