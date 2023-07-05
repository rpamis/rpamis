package com.rpamis.common.trace.core;

import com.rpamis.common.dto.trace.Trace;
import com.rpamis.common.trace.utils.TraceIdUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import java.util.Enumeration;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Feign TraceId Interceptor
 *
 * @author benym
 * @date 2022/7/10 22:00
 */
@Configuration
public class FeignRequestInterceptor implements RequestInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(FeignRequestInterceptor.class);

    @Autowired
    private TraceIdUtil traceIdUtil;

    /**
     * 为远程调用增加Trace
     *
     * @param requestTemplate requestTemplate
     */
    @Override
    public void apply(RequestTemplate requestTemplate) {
        logger.info("开始远程调用{}", requestTemplate);
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
            .getRequestAttributes();
        // 传递请求相关header
        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();
            Enumeration<String> headerNames = request.getHeaderNames();
            if (headerNames != null) {
                while (headerNames.hasMoreElements()) {
                    String name = headerNames.nextElement();
                    if (Objects.equals("content-length", name)){
                        continue;
                    }
                    String value = request.getHeader(name);
                    requestTemplate.header(name, value);
                }
            }
        }
        // 传递TraceId
        setHeaderFromMdc(requestTemplate, Trace.TRACE_ID, traceIdUtil.getTrace().getTraceId());
        // 传递SpanId
        setHeaderFromMdc(requestTemplate, Trace.SPAN_ID, traceIdUtil.getTrace().getSpanId());
    }

    /**
     * 从MDC获取Trace并设置header值
     *
     * @param requestTemplate requestTemplate
     * @param headerName headerName
     * @param defaultValue defaultValue
     */
    private void setHeaderFromMdc(RequestTemplate requestTemplate, String headerName, String defaultValue) {
        String value = MDC.get(headerName);
        if (value != null) {
            requestTemplate.header(headerName, value);
        } else {
            requestTemplate.header(headerName, defaultValue);
        }
    }
}
