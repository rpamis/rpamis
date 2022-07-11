package com.benym.rpas.common.core.exception;


import com.benym.rpas.common.dto.enums.ResponseCode;
import com.benym.rpas.common.dto.enums.Trace;
import com.benym.rpas.common.dto.exception.BizException;
import com.benym.rpas.common.dto.exception.RpasException;
import com.benym.rpas.common.dto.exception.SysException;
import com.benym.rpas.common.dto.response.Response;
import com.benym.rpas.common.utils.TraceIdUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

/**
 * @Time : 2022/7/7 22:04
 */
@RestControllerAdvice
public class ExceptionErrorHandler {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionErrorHandler.class);

    @ExceptionHandler(org.springframework.validation.BindException.class)
    public ResponseEntity<Response<Object>> handleBindException(BindException bindException) {
        final Trace trace = TraceIdUtils.getTrace();
        String validateMessage = Objects.requireNonNull(bindException.getFieldError()).getDefaultMessage();
        logger.error("请求Id:{}, SpanId:{}, 参数校验失败:{}", trace.getTraceId(), trace.getSpanId(), validateMessage);
        if (logger.isDebugEnabled()) {
            logger.error(bindException.getMessage(), bindException);
        }
        final Response<Object> failResponse = Response.fail(ResponseCode.VALIDATE_ERROR, validateMessage);
        failResponse.setTraceId(trace.getTraceId());
        return new ResponseEntity<>(failResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Response<Object>> handleNotReadException(HttpMessageNotReadableException notReadableException) {
        final Trace trace = TraceIdUtils.getTrace();
        logger.error("请求Id:{}, SpanId:{}, 错误码:{}, 错误信息:{}, 详细信息:{}", trace.getTraceId(), trace.getSpanId(),
                ResponseCode.READ_JSON_ERROR.getCode(), ResponseCode.READ_JSON_ERROR.getMessage(), notReadableException.getMessage());
        if (logger.isDebugEnabled()) {
            logger.error(notReadableException.getMessage(), notReadableException);
        }
        final Response<Object> failResponse = Response.fail(ResponseCode.READ_JSON_ERROR, ResponseCode.READ_JSON_ERROR.getMessage());
        failResponse.setTraceId(trace.getTraceId());
        return new ResponseEntity<>(failResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Response<Object>> handleParameterException(MissingServletRequestParameterException misException) {
        final Trace trace = TraceIdUtils.getTrace();
        String missParams = String.format("%s参数, 类型%s缺失", misException.getParameterName(),misException.getParameterType());
        logger.error("请求Id:{} ,SpanId:{} ,详细信息:{}",trace.getTraceId(), trace.getSpanId(), missParams);
        if (logger.isDebugEnabled()) {
            logger.error(misException.getMessage(), misException);
        }
        final Response<Object> failResponse = Response.fail(ResponseCode.INVALID_PARAMETER, missParams);
        failResponse.setTraceId(trace.getTraceId());
        return new ResponseEntity<>(failResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BizException.class)
    public ResponseEntity<Response<Object>> handleBizException(BizException bizException) {
        final Trace trace = TraceIdUtils.getTrace();
        String errCode = bizException.getErrCode();
        String message = bizException.getMessage();
        String detailMessage = bizException.getDetailMessage();
        logger.error("请求Id:{}, SpanId:{}, 业务异常:{}, 错误码:{}, 详细信息:{}", trace.getTraceId(), trace.getSpanId(), message, errCode, detailMessage);
        if (logger.isDebugEnabled()) {
            logger.error(message, bizException);
        }
        final Response<Object> failResponse = Response.fail(ResponseCode.BIZ_EXCEPTION_CODE, detailMessage);
        failResponse.setTraceId(trace.getTraceId());
        return new ResponseEntity<>(failResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(SysException.class)
    public ResponseEntity<Response<Object>> handleSysException(SysException sysException) {
        final Trace trace = TraceIdUtils.getTrace();
        String errCode = sysException.getErrCode();
        String message = sysException.getMessage();
        String detailMessage = sysException.getDetailMessage();
        logger.error("请求Id:{}, SpanId:{}, 系统异常:{}, 错误码:{}, 详细信息:{}", trace.getTraceId(), trace.getSpanId(), message, errCode, detailMessage);
        if (logger.isDebugEnabled()) {
            logger.error(message, sysException);
        }
        final Response<Object> failResponse = Response.fail(ResponseCode.SYS_EXCEPTION_CODE, detailMessage);
        failResponse.setTraceId(trace.getTraceId());
        return new ResponseEntity<>(failResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RpasException.class)
    public ResponseEntity<Response<Object>> handleRpasException(RpasException rpasException) {
        final Trace trace = TraceIdUtils.getTrace();
        String errCode = rpasException.getErrCode();
        String message = rpasException.getMessage();
        String detailMessage = rpasException.getDetailMessage();
        logger.error("请求Id:{}, SpanId:{}, 系统内部异常:{}, 错误码:{}, 详细信息:{}", trace.getTraceId(), trace.getSpanId(), message, errCode, detailMessage);
        if (logger.isDebugEnabled()) {
            logger.error(message, rpasException);
        }
        final Response<Object> failResponse = Response.fail(ResponseCode.RPAS_EXCEPTION_CODE, detailMessage);
        failResponse.setTraceId(trace.getTraceId());
        return new ResponseEntity<>(failResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response<Object>> handleException(Exception exception) {
        final Trace trace = TraceIdUtils.getTrace();
        logger.error("请求ID:{}, SpanId:{}, 未知异常:{}, 详细信息:{}", trace.getTraceId(), trace.getSpanId(), exception.getMessage(), exception);
        if (logger.isDebugEnabled()) {
            logger.error(exception.getMessage(), exception);
        }
        final Response<Object> failResponse = Response.fail(ResponseCode.UNKNOWN_EXCEPTION_CODE, exception.getMessage());
        failResponse.setTraceId(trace.getTraceId());
        return new ResponseEntity<>(failResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
