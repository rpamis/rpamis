package com.benym.rpamis.common.trace.core;

import com.benym.rpamis.common.dto.enums.Trace;
import com.benym.rpamis.common.utils.TraceIdUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author benym
 * @date 2022/7/10 22:00
 */
@Configuration
public class FeignRequestInterceptor implements RequestInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(FeignRequestInterceptor.class);

    @Autowired
    private TraceIdUtils traceIdUtils;

    /**
     * 为远程调用增加traceId
     */
    @Override
    public void apply(RequestTemplate requestTemplate) {
        logger.info("开始远程调用{}", requestTemplate);
        String traceId = MDC.get(Trace.TRACE_ID);
        if (traceId != null) {
            requestTemplate.header(Trace.TRACE_ID, traceId);
        } else {
            requestTemplate.header(Trace.TRACE_ID, traceIdUtils.getTrace().getTraceId());
        }
    }
}
