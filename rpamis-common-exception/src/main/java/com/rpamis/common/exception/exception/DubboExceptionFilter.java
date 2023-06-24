package com.rpamis.common.exception.exception;

import cn.hutool.json.JSONUtil;
import com.rpamis.common.dto.enums.ResponseCode;
import com.rpamis.common.dto.enums.Trace;
import com.rpamis.common.dto.exception.*;
import com.rpamis.common.dto.response.Response;
import com.rpamis.common.exception.properties.ExceptionProperties;
import com.rpamis.common.utils.TraceIdUtil;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.ExceptionHandlerMethodResolver;

import java.lang.reflect.Method;
import java.util.Optional;

import static org.apache.dubbo.common.constants.CommonConstants.CONSUMER;
import static org.apache.dubbo.common.constants.CommonConstants.PROVIDER;

/**
 * 全局dubbo异常处理，统一返回体，避免consumer端try catch手动处理rpc异常
 * 当自定义异常处理中出现问题
 * 会被dubbo本身的com.alibaba.dubbo.rpc.filter.ExceptionFilter捕获进行异常兜底和日志打印
 * 使用时需要在resource目录下新建META-INFO/dubbo/com.alibaba.dubbo.rpc.Filter，并在文件中填写DubboExceptionFilter(可自定义名字)
 * DubboExceptionFilter=cn.rpamis.common.core.exception.DubboExceptionFilter
 * 同时配置dubbo-providers.xml <dubbo:provider filter="DubboExceptionFilter"/>
 *
 * @author benym
 * @date 2022/11/3 16:31
 */
@Activate(group = {PROVIDER, CONSUMER}, order = Ordered.HIGHEST_PRECEDENCE)
public class DubboExceptionFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(DubboExceptionFilter.class);

    private ExceptionProperties exceptionProperties;

    private TraceIdUtil traceIdUtil;

    public void setTraceIdUtil(TraceIdUtil traceIdUtil) {
        this.traceIdUtil = traceIdUtil;
    }

    public void setExceptionProperties(ExceptionProperties exceptionProperties) {
        this.exceptionProperties = exceptionProperties;
    }

    /**
     * 全局dubbo异常处理，注意受检异常问题，dubbo可能会将其他的异常包成RuntimeException
     * 场景为：provider抛自定义异常、但consumer却收到的是RuntimeException
     * 在有Activate注解情况下，理论上无需配置dubbo-provider中的filter或application中的dubbo.provider.filter
     * 在dubbo2.5.x以下需要配置xml，可以防止该问题
     * 该问题只在provider端没有做统一返回体时，对consumer处理判断有影响
     * 该过滤器拦截异常并做统一返回体，对consumer处理无影响
     *
     * @param invoker    invoker
     * @param invocation invocation
     * @return Result
     * @throws RpcException
     */
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        final Trace trace = traceIdUtil.getTrace();
        String params = JSONUtil.toJsonStr(invocation.getArguments());
        logger.info("Global dubbo exception filter, requestId:{}, spanId:{}, interface:{}, methodName:{}, params:{}",
                trace.getTraceId(), trace.getSpanId(), invoker.getInterface(), invocation.getMethodName(), params);
        Result result = invoker.invoke(invocation);
        if (result.hasException()) {
            try {
                ExceptionHandlerMethodResolver resolver = new ExceptionHandlerMethodResolver(this.getClass());
                // 获取RPC过程中出现的具体异常
                Exception exception = (Exception) result.getException();
                // 从@ExceptionHandler注解方法中找到value为exception方法的特定对象
                Method method = resolver.resolveMethod(exception);
                // 找到具体的异常处理类，执行对应处理，这里即返回RemoteResult
                if (method != null) {
                    Object value = method.invoke(this, exception);
                    // 是否开启返回体包装，未开启或无property注入则直接返回
                    if (exceptionProperties != null && !exceptionProperties.getRpcPack()) {
                        return result;
                    }
                    result.setValue(value);
                }
                return result;
            } catch (Exception e) {
                logger.error("Dubbo exception filter error, Casued by ", e);
            }
        }
        return result;
    }

    @ExceptionHandler(ValidException.class)
    public Object handleValidException(ValidException exception) {
        final Trace trace = traceIdUtil.getTrace();
        logger.warn("catch dubbo validExpcetion, requestId:{}, spanId:{}, exception is:",
                trace.getTraceId(), trace.getSpanId(), exception);
        Optional<ValidException> opValid = Optional.ofNullable(exception);
        String errCode = opValid.map(AbstractException::getErrCode)
                .orElse(ResponseCode.VALID_EXCEPTION_CODE.getCode());
        String errMessage = opValid.map(AbstractException::getErrMessage)
                .orElse(ResponseCode.VALID_EXCEPTION_CODE.getMessage());
        return Response.fail(errCode, errMessage);
    }

    @ExceptionHandler(BizException.class)
    public Object handleBizException(BizException exception) {
        final Trace trace = traceIdUtil.getTrace();
        logger.warn("catch dubbo bizExpcetion, requestId:{}, spanId:{}, exception is:",
                trace.getTraceId(), trace.getSpanId(), exception);
        Optional<BizException> opBiz = Optional.ofNullable(exception);
        String errCode = opBiz.map(AbstractException::getErrCode)
                .orElse(ResponseCode.BIZ_EXCEPTION_CODE.getCode());
        String errMessage = opBiz.map(AbstractException::getErrMessage)
                .orElse(ResponseCode.BIZ_EXCEPTION_CODE.getMessage());
        return Response.fail(errCode, errMessage);
    }

    @ExceptionHandler(BizNoStackException.class)
    public Object handleBizNoStackException(BizNoStackException exception) {
        final Trace trace = traceIdUtil.getTrace();
        logger.warn("catch dubbo bizNoStackException, requestId:{}, spanId:{}, exception is:",
                trace.getTraceId(), trace.getSpanId(), exception);
        Optional<BizNoStackException> opValid = Optional.ofNullable(exception);
        String errCode = opValid.map(AbstractException::getErrCode)
                .orElse(ResponseCode.VALID_EXCEPTION_CODE.getCode());
        String errMessage = opValid.map(AbstractException::getErrMessage)
                .orElse(ResponseCode.VALID_EXCEPTION_CODE.getMessage());
        return Response.fail(errCode, errMessage);
    }

    @ExceptionHandler(SysException.class)
    public Object handleSysException(SysException exception) {
        final Trace trace = traceIdUtil.getTrace();
        logger.error("catch dubbo sysExpcetion, requestId:{}, spanId:{}, exception is:",
                trace.getTraceId(), trace.getSpanId(), exception);
        Optional<SysException> opSys = Optional.ofNullable(exception);
        String errCode = opSys.map(AbstractException::getErrCode)
                .orElse(ResponseCode.SYS_EXCEPTION_CODE.getCode());
        String errMessage = opSys.map(AbstractException::getErrMessage)
                .orElse(ResponseCode.SYS_EXCEPTION_CODE.getMessage());
        return Response.fail(errCode, errMessage);
    }

    @ExceptionHandler(RpamisException.class)
    public Object handleRpasException(RpamisException exception) {
        final Trace trace = traceIdUtil.getTrace();
        logger.error("catch dubbo rpasExpcetion, requestId:{}, spanId:{}, exception is:",
                trace.getTraceId(), trace.getSpanId(), exception);
        Optional<RpamisException> opRpas = Optional.ofNullable(exception);
        String errCode = opRpas.map(RpamisException::getErrCode)
                .orElse(ResponseCode.RPAS_EXCEPTION_CODE.getCode());
        String errMessage = opRpas.map(RpamisException::getErrMessage)
                .orElse(ResponseCode.RPAS_EXCEPTION_CODE.getMessage());
        return Response.fail(errCode, errMessage);
    }

    @ExceptionHandler(Exception.class)
    public Object handleException(Exception exception) {
        final Trace trace = traceIdUtil.getTrace();
        logger.error("catch dubbo unknown Expcetion, requestId:{}, spanId:{}, exception is:",
                trace.getTraceId(), trace.getSpanId(), exception);
        Optional<Exception> opExcetion = Optional.ofNullable(exception);
        String errCode = ResponseCode.UNKNOWN_EXCEPTION_CODE.getCode();
        String errMessage = opExcetion.map(Exception::getMessage)
                .orElse(ResponseCode.UNKNOWN_EXCEPTION_CODE.getMessage());
        return Response.fail(errCode, errMessage);
    }
}
