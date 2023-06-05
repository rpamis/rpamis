package com.rpamis.common.trace.logger;

import com.rpamis.common.trace.core.TraceRequestWrapper;
import com.rpamis.common.trace.core.TraceResponseWrapper;
import com.rpamis.common.dto.request.RequestLog;
import com.rpamis.common.utils.JackSonUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

/**
 * Http日志实体
 *
 * @author benym
 * @date 2022/7/12 7:05 下午
 */
public class LoggerHttp {

    private LoggerHttp() {
        // 空构造
    }

    public static RequestLog init(TraceRequestWrapper traceRequestWrapper) {
        RequestLog requestLog = new RequestLog();
        requestLog.setRequestUrl(traceRequestWrapper.getRequestURI());
        requestLog.setRemoteAddr(traceRequestWrapper.getRemoteAddr());
        requestLog.setRequestHeaders(traceRequestWrapper.getHeaders());
        String method = traceRequestWrapper.getMethod();
        requestLog.setMethod(method);
        if (method.equals(RequestMethod.GET.name())) {
            requestLog.setRequestParams(traceRequestWrapper.getParameterMap());
        } else {
            requestLog.setRequestParams(getPostData(traceRequestWrapper));
        }
        return requestLog;
    }

    public static void update(@NotNull RequestLog requestLog, TraceResponseWrapper traceResponseWrapper, long totalTime) throws IOException {
        requestLog.setStatus(traceResponseWrapper.getStatus());
        Optional<String> contentType = Optional.ofNullable(traceResponseWrapper.getContentType());
        if (contentType.isPresent() && "application/json".equals(contentType.get())) {
            requestLog.setResponse(JackSonUtils.toMap(
                    new String(traceResponseWrapper.getContentAsByteArray())));
        }
        traceResponseWrapper.copyBodyToResponse();
        requestLog.setResponseHeaders(traceResponseWrapper.getHeaders());
        requestLog.setTotalTime(totalTime);
    }

    public static Map<Object, Object> getPostData(TraceRequestWrapper traceRequestWrapper) {
        return JackSonUtils.toMap(traceRequestWrapper.getPostData());
    }
}
