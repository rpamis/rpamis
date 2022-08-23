package com.benym.rpas.common.core.logger;

import com.benym.rpas.common.core.trace.TraceRequestWrapper;
import com.benym.rpas.common.core.trace.TraceResponseWrapper;
import com.benym.rpas.common.dto.request.RequestLog;
import com.benym.rpas.common.utils.JackSonUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.Map;

/**
 * @date 2022/7/12 7:05 下午
 */
public class LoggerHttp {

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

    public static void update(RequestLog requestLog, TraceResponseWrapper traceResponseWrapper, long totalTime) throws IOException {
        requestLog.setStatus(traceResponseWrapper.getStatus());
        requestLog.setResponse(JackSonUtils.toMap(new String(traceResponseWrapper.getContentAsByteArray())));
        traceResponseWrapper.copyBodyToResponse();
        requestLog.setResponseHeaders(traceResponseWrapper.getHeaders());
        requestLog.setTotalTime(totalTime);
    }

    public static Map<Object, Object> getPostData(TraceRequestWrapper traceRequestWrapper) {
        return JackSonUtils.toMap(traceRequestWrapper.getPostData());
    }
}
