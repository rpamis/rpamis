package com.benym.rpas.common.pojo.enums;

/**
 * @Time : 2022/7/6 21:18
 */

public enum ResponseCode implements StatusCode {

    SUCCESS(0, "请求成功"),
    FAILED(500, "请求失败"),
    VALIDATE_ERROR(20000, "参数校验失败");

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
