package com.benym.rpas.common.core.exception;


import com.benym.rpas.common.dto.enums.ResponseCode;
import com.benym.rpas.common.dto.enums.TraceType;
import com.benym.rpas.common.dto.exception.BizException;
import com.benym.rpas.common.dto.exception.RpasException;
import com.benym.rpas.common.dto.exception.SysException;
import com.benym.rpas.common.dto.response.Response;
import com.benym.rpas.common.utils.TraceIdUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

/**
 * @Time : 2022/7/7 22:04
 */
@RestControllerAdvice
public class ExceptionErrorHandler {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionErrorHandler.class);

    @Value("${trace.type:rpas}")
    private String traceType;

    @ExceptionHandler(org.springframework.validation.BindException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<Response<Object>> handleBindException(BindException bindException) {
        final String traceId = TraceIdUtils.getTraceId(traceType);
        String validateMessage = Objects.requireNonNull(bindException.getFieldError()).getDefaultMessage();
        logger.error("请求ID:{}, 参数校验失败:{}", traceId, validateMessage);
        if (logger.isDebugEnabled()) {
            logger.error(bindException.getMessage(), bindException);
        }
        final Response<Object> failResponse = Response.fail(ResponseCode.VALIDATE_ERROR, validateMessage);
        failResponse.setTraceId(traceId);
        return new ResponseEntity<>(failResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BizException.class)
    public ResponseEntity<Response<Object>> handleBizException(BizException bizException) {
        final String traceId = TraceIdUtils.getTraceId(traceType);
        int errCode = bizException.getErrCode();
        String message = bizException.getMessage();
        String detailMessage = bizException.getDetailMessage();
        logger.error("请求ID:{}, 业务异常:{}, 错误码:{},详细信息:{}", traceId, message, errCode, detailMessage);
        if (logger.isDebugEnabled()) {
            logger.error(message, bizException);
        }
        final Response<Object> failResponse = Response.fail(ResponseCode.BIZ_EXCEPTION_CODE, detailMessage);
        failResponse.setTraceId(traceId);
        return new ResponseEntity<>(failResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(SysException.class)
    public ResponseEntity<Response<Object>> handleBizException(SysException sysException) {
        final String traceId = TraceIdUtils.getTraceId(traceType);
        int errCode = sysException.getErrCode();
        String message = sysException.getMessage();
        String detailMessage = sysException.getDetailMessage();
        logger.error("请求ID:{}, 系统异常:{}, 错误码:{},详细信息:{}", traceId, message, errCode, detailMessage);
        if (logger.isDebugEnabled()) {
            logger.error(message, sysException);
        }
        final Response<Object> failResponse = Response.fail(ResponseCode.SYS_EXCEPTION_CODE, detailMessage);
        failResponse.setTraceId(traceId);
        return new ResponseEntity<>(failResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RpasException.class)
    public ResponseEntity<Response<Object>> handleBizException(RpasException rpasException) {
        final String traceId = TraceIdUtils.getTraceId(traceType);
        int errCode = rpasException.getErrCode();
        String message = rpasException.getMessage();
        String detailMessage = rpasException.getDetailMessage();
        logger.error("请求ID:{}, 系统内部异常:{}, 错误码:{},详细信息:{}", traceId, message, errCode, detailMessage);
        if (logger.isDebugEnabled()) {
            logger.error(message, rpasException);
        }
        final Response<Object> failResponse = Response.fail(ResponseCode.RPAS_EXCEPTION_CODE, detailMessage);
        failResponse.setTraceId(traceId);
        return new ResponseEntity<>(failResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response<Object>> handleBizException(Exception exception) {
        final String traceId = TraceIdUtils.getTraceId(traceType);
        logger.error("请求ID:{}, 未知异常:{}, 详细信息:{}", traceId, exception.getMessage(), exception);
        if (logger.isDebugEnabled()) {
            logger.error(exception.getMessage(), exception);
        }
        final Response<Object> failResponse = Response.fail(ResponseCode.UNKNOWN_EXCEPTION_CODE, exception.getMessage());
        failResponse.setTraceId(traceId);
        return new ResponseEntity<>(failResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
