package com.benym.rpas.common.dto.enums;

/**
 * @Time : 2022/7/6 21:18
 */

public enum ResponseCode implements StatusCode {

    SUCCESS(0, "请求成功"),
    FAILED(500, "请求失败"),
    BIZ_EXCEPTION_CODE(20000,"业务异常"),
    SYS_EXCEPTION_CODE(20001,"系统异常"),
    VALIDATE_ERROR(20002, "参数校验失败");

    private final int code;
    private final String message;

    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
