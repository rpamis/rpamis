package com.rpamis.common.exception.exception;

import com.rpamis.common.dto.enums.ResponseCode;
import com.rpamis.common.dto.enums.Trace;
import com.rpamis.common.dto.response.Response;
import com.rpamis.common.utils.TraceIdUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.Resource;

/**
 * 全局异常Exception.class捕获，独立出文件用于yml控制
 * 这里Order+1是因为需要将最大的Exception.class异常置于自定义异常之后
 * 避免同一个执行顺序，由Spring决定，导致抛出的是自定义异常，却先被Exception.class处理
 *
 * @author benym
 * @date 2023/2/26 19:35
 */
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
public class ExceptionBaseHandler {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionBaseHandler.class);

    @Resource
    private TraceIdUtil traceIdUtil;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response<Object>> handleException(Exception exception) {
        final Trace trace = traceIdUtil.getTrace();
        logger.error("请求ID:{}, SpanId:{}, 未知异常:{}, 详细信息:", trace.getTraceId(), trace.getSpanId(), exception.getMessage(), exception);
        final Response<Object> failResponse = Response.fail(ResponseCode.UNKNOWN_EXCEPTION_CODE, exception.getMessage());
        return new ResponseEntity<>(failResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
