package com.benym.rpas.common.dto.enums;

/**
 * @Time : 2022/7/6 21:18
 */
public enum ResponseCode implements StatusCode {

    SUCCESS("Success", "请求成功"),
    FAILED("Failed", "请求失败"),
    BIZ_EXCEPTION_CODE("BizException","业务异常"),
    SYS_EXCEPTION_CODE("SysException","系统异常"),
    RPAS_EXCEPTION_CODE("InternalException","系统内部异常"),
    UNKNOWN_EXCEPTION_CODE("UnknownException","未知异常"),
    VALIDATE_ERROR("ValidateError", "参数校验失败"),
    READ_JSON_ERROR("ReadJsonError","Json格式不正确"),
    INVALID_PARAMETER("InvalidParameter","参数错误");

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
