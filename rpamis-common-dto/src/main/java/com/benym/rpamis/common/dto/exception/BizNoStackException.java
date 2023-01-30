package com.benym.rpamis.common.dto.exception;

import com.benym.rpamis.common.dto.enums.ResponseCode;
import com.benym.rpamis.common.dto.enums.StatusCode;

import java.io.Serializable;

/**
 * 业务异常类，固定状态码，不带堆栈
 *
 * @author benym
 * @date 2023/1/13 17:48
 */
public class BizNoStackException extends AbstractException implements Serializable {

    private static final long serialVersionUID = 2628908675799105091L;

    private static final ResponseCode DEAULT_BIZ_ERRCODE = ResponseCode.BIZ_NOSTACK_EXCEPTION;

    public BizNoStackException() {
        super(DEAULT_BIZ_ERRCODE);
    }

    public BizNoStackException(String errMessage) {
        super(DEAULT_BIZ_ERRCODE.getCode(), errMessage);
    }

    public BizNoStackException(String errCode, String errMessage) {
        super(errCode, errMessage);
    }

    public BizNoStackException(StatusCode statusCode) {
        super(statusCode.getCode(), statusCode.getMessage());
    }
}
