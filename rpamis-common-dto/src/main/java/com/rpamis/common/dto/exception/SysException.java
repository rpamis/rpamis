package com.rpamis.common.dto.exception;

import com.rpamis.common.dto.enums.ResponseCode;
import com.rpamis.common.dto.enums.StatusCode;

/**
 * 定义系统异常类，固定状态码 This class is empowered by com.alibaba.cola
 *
 * @author benym
 * @date 2022/7/7 22:42
 */
public class SysException extends AbstractException {

  private static final long serialVersionUID = 1L;

  private static final ResponseCode DEFAULT_SYS_ERRCODE = ResponseCode.SYS_EXCEPTION_CODE;

  public SysException(String errMessage, Throwable e) {
    super(DEFAULT_SYS_ERRCODE.getCode(), errMessage, e);
  }

  public SysException(StatusCode statusCode, Throwable e) {
    super(statusCode.getCode(), statusCode.getMessage(), e);
  }

  public SysException(String errCode, String errMessage, Throwable e) {
    super(errCode, errMessage, e);
  }

  public SysException(Throwable e) {
    super(DEFAULT_SYS_ERRCODE, e);
  }
}
