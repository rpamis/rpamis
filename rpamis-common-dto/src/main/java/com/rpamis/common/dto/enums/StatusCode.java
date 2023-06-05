package com.rpamis.common.dto.enums;

/**
 * 状态码通用接口
 *
 * @author benym
 * @date 2022/7/6 21:02
 */
public interface StatusCode {

    /**
     * 状态码
     */
    String getCode();

    /**
     * 消息
     */
    String getMessage();
}
