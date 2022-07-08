package com.benym.rpas.common.core.exception;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Time : 2022/7/7 22:04
 */
@RestControllerAdvice
public class ExceptionErrorHandler {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionErrorHandler.class);

    @ExceptionHandler(org.springframework.validation.BindException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<Response<Object>> handBindException(BindException bindException) {
        final String requestId = TraceIdUtils.getTraceId();
        String validateMessage = bindException.getFieldError().getDefaultMessage();
        logger.error("请求ID:{}, 参数校验失败：{}", requestId, validateMessage);
        if (logger.isDebugEnabled()) {
            logger.error(bindException.getMessage(), bindException);
        }
        final Response<Object> failResponse = Response.fail(ResponseCode.VALIDATE_ERROR,validateMessage);
        failResponse.setRequestId(requestId);
        return new ResponseEntity<Response<Object>>(failResponse,HttpStatus.BAD_REQUEST);
    }
}
