package com.benym.rpas.common.core.trace;

import com.benym.rpas.common.dto.enums.Trace;
import com.benym.rpas.common.utils.SnowflakeUtils;
import com.benym.rpas.common.utils.TraceIdUtils;
import java.io.IOException;
import java.util.Objects;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @Time : 2022/7/7 22:01
 */
@Order
@WebFilter(filterName = "traceFilter", urlPatterns = {"/*"})
@Component
public class TraceFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(TraceFilter.class);

    public TraceFilter() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        Trace trace = TraceIdUtils.getTrace();
        TraceRequestWrapper traceRequestWrapper = null;
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            if (httpServletRequest.getHeader(Trace.TRACE_ID) == null) {
                traceRequestWrapper = new TraceRequestWrapper(httpServletRequest);
                traceRequestWrapper.putHeader(Trace.TRACE_ID, trace.getTraceId());
                traceRequestWrapper.putHeader(Trace.SPAN_ID, trace.getSpanId());
            }
        }
        try {
            chain.doFilter(Objects.nonNull(traceRequestWrapper) ? traceRequestWrapper : request, response);
            // doFilter之后表示当前请求结束，下一次属于另外一个Span
            MDC.put(Trace.SPAN_ID, String.valueOf(SnowflakeUtils.get().next()));
            // 请求完成之后同步清理traceId
            TraceIdUtils.clearTrace();
        } catch (Exception e) {
            logger.warn("traceId Filter error{}", e.getMessage());
        }

    }
}
