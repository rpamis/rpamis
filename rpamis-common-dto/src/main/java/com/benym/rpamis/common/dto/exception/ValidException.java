package com.benym.rpamis.common.dto.exception;

import com.benym.rpamis.common.dto.enums.ResponseCode;
import com.benym.rpamis.common.dto.enums.StatusCode;

/**
 * 校验异常类，固定状态码，不打印堆栈信息
 *
 * @author benym
 * @date 2022/11/11 20:36
 */
public class ValidException extends AbstractException {

    private static final long serialVersionUID = 1L;

    private static final ResponseCode DEFAULT_VALID_ERRCODE = ResponseCode.VALID_EXCEPTION_CODE;

    public ValidException() {
        super(DEFAULT_VALID_ERRCODE);
    }

    public ValidException(String errMessage) {
        super(DEFAULT_VALID_ERRCODE.getCode(), errMessage);
    }

    public ValidException(String errCode, String errMessage) {
        super(errCode, errMessage);
    }

    public ValidException(StatusCode statusCode) {
        super(statusCode.getCode(), statusCode.getMessage());
    }
}
