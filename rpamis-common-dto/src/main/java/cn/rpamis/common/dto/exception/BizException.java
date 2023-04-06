package cn.rpamis.common.dto.exception;

import cn.rpamis.common.dto.enums.ResponseCode;
import cn.rpamis.common.dto.enums.StatusCode;

/**
 * 定义业务异常类，固定状态码
 * This class is empowered by com.alibaba.cola
 *
 * @author benym
 * @date 2022/7/7 22:37
 */
public class BizException extends AbstractException {

    private static final long serialVersionUID = 1L;

    private static final ResponseCode DEAULT_BIZ_ERRCODE = ResponseCode.BIZ_EXCEPTION_CODE;

    public BizException(String errMessage, Throwable e) {
        super(DEAULT_BIZ_ERRCODE.getCode(), errMessage, e);
    }

    public BizException(StatusCode statusCode, Throwable e) {
        super(statusCode.getCode(), statusCode.getMessage(), e);
    }

    public BizException(String errCode, String errMessage, Throwable e) {
        super(errCode, errMessage, e);
    }

    public BizException(Throwable e) {
        super(DEAULT_BIZ_ERRCODE, e);
    }
}
