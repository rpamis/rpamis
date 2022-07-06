package com.benym.rpas.common.pojo.enums;

/**
 * @Time : 2022/7/6 21:18
 */

public enum ResponseCode implements StatusCode{

    SUCCESS(20000,"请求成功"),
    FAILED(20001,"请求失败"),
    VALIDATE_ERROR(20002,"参数校验失败");

    private int code;
    private String message;

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
