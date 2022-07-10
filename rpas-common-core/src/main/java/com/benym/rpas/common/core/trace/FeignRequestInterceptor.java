package com.benym.rpas.common.core.trace;

import com.benym.rpas.common.dto.enums.Trace;
import com.benym.rpas.common.utils.TraceIdUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;

/**
 * @Time : 2022/7/10 22:00
 */
@Configuration
public class FeignRequestInterceptor implements RequestInterceptor {

    /**
     * 为远程调用增加traceId
     */
    @Override
    public void apply(RequestTemplate requestTemplate) {
        String traceId = MDC.get(Trace.TRACE_ID);
        if (traceId != null) {
            requestTemplate.header(Trace.TRACE_ID, traceId);
        } else {
            requestTemplate.header(Trace.TRACE_ID, TraceIdUtils.getTrace().getTraceId());
        }
    }
}
