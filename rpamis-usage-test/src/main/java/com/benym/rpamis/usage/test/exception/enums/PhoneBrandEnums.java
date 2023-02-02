package com.benym.rpamis.usage.test.exception.enums;

import com.benym.rpamis.common.dto.enums.StatusCode;

/**
 * @author benym
 * @date 2023/2/2 17:21
 */
public enum PhoneBrandEnums implements StatusCode {

    /**
     * 苹果
     */
    IPHONE("0", "苹果手机"),
    /**
     * 华为
     */
    HUAWEI("1", "华为手机"),
    /**
     * 小米
     */
    XIAOMI("2", "小米手机");

    private String code;

    private String message;

    PhoneBrandEnums(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
