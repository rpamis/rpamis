package com.rpamis.common.exception.exception;


import com.rpamis.common.dto.enums.ResponseCode;
import com.rpamis.common.dto.enums.Trace;
import com.rpamis.common.dto.exception.*;
import com.rpamis.common.dto.response.Response;
import com.rpamis.common.utils.TraceIdUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * SpringBoot使用时请采用
 * <p/>@ComponentScan({"你的启动类基础package路径","cn.rpamis.common.exception"})
 * 非SpringBoot项目请采用XML文件方式
 * <p/><context:component-scan base-package="你的启动类基础package路径, cn.rpamis.common.exception"/>
 * 全局异常捕获，目前主要捕获spring的Validated和javax的Valid注解，和自定义异常类，全局异常类
 * 处理顺序从上至下，被上层异常处理过的不会再被之后的异常处理
 * 适用于以Controller作为入口的接口异常捕获
 * 包括Controller进入后调用的Service、Manager、Mapper等出现的异常
 * <p/>
 * 日志级别WARN:对于前置校验类异常，正常来说状态码为400，代表前端参数错误，400状态下前端不能直接拿到返回体，需要前端异常捕获配合才能打印msg，该类型异常已知，不需要人工处理
 * 日志级别WARN:对于业务类校验异常ValidException(不带堆栈)，状态码为200，表示请求正常只是业务拦截，该类型异常已知，不需要人工处理
 * 日志级别WARN：对于业务类异常BizException(带堆栈)、BizNoStackException(不带堆栈)，状态码200，表示请求正常只是业务拦截，该类型异常已知，不需要人工处理
 * 日志级别ERROR:对于已知可能发生的系统级异常SysException(带堆栈)，状态码为500，表示出现系统异常，开发者手动抛出该异常说明，该系统级异常已知，需要人工处理
 * 日志级别ERROR:对于未知的发生的系统级异常Exception(带堆栈)，状态码500，表示出现未知的没有被try catch的异常，需要人工处理
 * 日志级别WARN:用于非固定状态码任意位置的异常RpasException(可带堆栈、也可不带)，状态码200，由于该类接受任意状态码，目的是兼容前端对接业务状态码场景，可用于兼容老项目做全局异常
 * <p/>
 * 强调http code规范，弱化业务code属性，业务code属性理论上属于后端开发需要观测，前端仅需根据http code做出对应处理
 *
 * @author benym
 * @date 2022/7/7 22:04
 */
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionErrorHandler {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionErrorHandler.class);

    @Resource
    private TraceIdUtils traceIdUtils;

    @ExceptionHandler(BindException.class)
    public ResponseEntity<Response<Object>> handleBindException(BindException bindException) {
        final Trace trace = traceIdUtils.getTrace();
        String validateMessage = Objects.requireNonNull(bindException.getBindingResult().getFieldError()).getDefaultMessage();
        logger.warn("[BindException], 请求Id:{}, SpanId:{}, 参数校验失败:{}", trace.getTraceId(), trace.getSpanId(), validateMessage);
        if (logger.isDebugEnabled()) {
            logger.debug(validateMessage, bindException);
        }
        final Response<Object> failResponse = Response.fail(ResponseCode.VALIDATE_ERROR, validateMessage);
        return new ResponseEntity<>(failResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Response<Object>> handleConsException(ConstraintViolationException constraintViolationException) {
        final Trace trace = traceIdUtils.getTrace();
        Set<ConstraintViolation<?>> constraintViolations = constraintViolationException.getConstraintViolations();
        String validateMessage = constraintViolations.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(";"));
        logger.warn("[ConstraintViolationException], 请求Id:{}, SpanId:{}, 参数校验失败:{}", trace.getTraceId(), trace.getSpanId(), validateMessage);
        if (logger.isDebugEnabled()) {
            logger.debug(validateMessage, constraintViolationException);
        }
        final Response<Object> failResponse = Response.fail(ResponseCode.VALIDATE_ERROR, validateMessage);
        return new ResponseEntity<>(failResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response<Object>> handleBindException(MethodArgumentNotValidException methodArgumentNotValidException) {
        final Trace trace = traceIdUtils.getTrace();
        String validateMessage = Objects.requireNonNull(methodArgumentNotValidException.getBindingResult().getFieldError()).getDefaultMessage();
        logger.warn("[MethodArgumentNotValidException], 请求Id:{}, SpanId:{}, 参数校验失败:{}", trace.getTraceId(), trace.getSpanId(), validateMessage);
        if (logger.isDebugEnabled()) {
            logger.debug(methodArgumentNotValidException.getMessage(), methodArgumentNotValidException);
        }
        final Response<Object> failResponse = Response.fail(ResponseCode.VALIDATE_ERROR, validateMessage);
        return new ResponseEntity<>(failResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Response<Object>> handleNotReadException(HttpMessageNotReadableException notReadableException) {
        final Trace trace = traceIdUtils.getTrace();
        logger.warn("请求Id:{}, SpanId:{}, 错误码:{}, 错误信息:{}, 详细信息:{}", trace.getTraceId(), trace.getSpanId(),
                ResponseCode.READ_JSON_ERROR.getCode(), ResponseCode.READ_JSON_ERROR.getMessage(), notReadableException.getMessage());
        if (logger.isDebugEnabled()) {
            logger.debug(notReadableException.getMessage(), notReadableException);
        }
        final Response<Object> failResponse = Response.fail(ResponseCode.READ_JSON_ERROR, ResponseCode.READ_JSON_ERROR.getMessage());
        return new ResponseEntity<>(failResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Response<Object>> handleParameterException(MissingServletRequestParameterException misException) {
        final Trace trace = traceIdUtils.getTrace();
        String missParams = String.format("%s参数, 类型%s缺失", misException.getParameterName(), misException.getParameterType());
        logger.warn("请求Id:{} ,SpanId:{} ,详细信息:{}", trace.getTraceId(), trace.getSpanId(), missParams);
        if (logger.isDebugEnabled()) {
            logger.debug(misException.getMessage(), misException);
        }
        final Response<Object> failResponse = Response.fail(ResponseCode.INVALID_PARAMETER, missParams);
        return new ResponseEntity<>(failResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidException.class)
    public ResponseEntity<Response<Object>> handleValidException(ValidException validException) {
        final Trace trace = traceIdUtils.getTrace();
        String errCode = validException.getErrCode();
        String message = validException.getMessage();
        logger.warn("请求Id:{}, SpanId:{}, 参数校验异常:{}, 错误码:{}", trace.getTraceId(), trace.getSpanId(), message, errCode);
        if (logger.isDebugEnabled()) {
            logger.debug(message, validException);
        }
        final Response<Object> failResponse = Response.fail(errCode, message);
        return new ResponseEntity<>(failResponse, HttpStatus.OK);
    }

    @ExceptionHandler(BizException.class)
    public ResponseEntity<Response<Object>> handleBizException(BizException bizException) {
        final Trace trace = traceIdUtils.getTrace();
        String errCode = bizException.getErrCode();
        String message = bizException.getMessage();
        logger.warn("请求Id:{}, SpanId:{}, 业务异常:{}, 错误码:{}, 详细信息:", trace.getTraceId(), trace.getSpanId(), message, errCode, bizException);
        final Response<Object> failResponse = Response.fail(errCode, message);
        return new ResponseEntity<>(failResponse, HttpStatus.OK);
    }

    @ExceptionHandler(BizNoStackException.class)
    public ResponseEntity<Response<Object>> handleBizNoStackException(BizNoStackException bizNoStackException) {
        final Trace trace = traceIdUtils.getTrace();
        String errCode = bizNoStackException.getErrCode();
        String message = bizNoStackException.getMessage();
        logger.warn("请求Id:{}, SpanId:{}, 业务异常(无堆栈):{}, 错误码:{}", trace.getTraceId(), trace.getSpanId(), message, errCode);
        if (logger.isDebugEnabled()) {
            logger.debug(message, bizNoStackException);
        }
        final Response<Object> failResponse = Response.fail(errCode, message);
        return new ResponseEntity<>(failResponse, HttpStatus.OK);
    }

    @ExceptionHandler(SysException.class)
    public ResponseEntity<Response<Object>> handleSysException(SysException sysException) {
        final Trace trace = traceIdUtils.getTrace();
        String errCode = sysException.getErrCode();
        String message = sysException.getMessage();
        logger.error("请求Id:{}, SpanId:{}, 系统异常:{}, 错误码:{}, 详细信息:", trace.getTraceId(), trace.getSpanId(), message, errCode, sysException);
        final Response<Object> failResponse = Response.fail(errCode, message);
        return new ResponseEntity<>(failResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RpamisException.class)
    public ResponseEntity<Response<Object>> handleRpasException(RpamisException rpamisException) {
        final Trace trace = traceIdUtils.getTrace();
        String errCode = rpamisException.getErrCode();
        String message = rpamisException.getMessage();
        String detailMessage = rpamisException.getDetailMessage();
        logger.error("请求Id:{}, SpanId:{}, 系统内部异常:{}, 错误码:{}, 详细信息:{}", trace.getTraceId(), trace.getSpanId(), message, errCode, detailMessage);
        if (logger.isDebugEnabled()) {
            logger.debug(message, rpamisException);
        }
        final Response<Object> failResponse = Response.fail(errCode, detailMessage);
        return new ResponseEntity<>(failResponse, HttpStatus.OK);
    }
}
