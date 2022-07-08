package com.benym.rpas.common.core.trace;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;
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
        String traceId = MDC.get("X-B3-TraceId");
        if (traceId == null) {
            traceId = UUID.randomUUID().toString();
            MDC.put("X-B3-TraceId", traceId);
        }
        TraceWrapper traceWrapper = null;
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            if (httpServletRequest.getHeader("X-B3-TraceId") == null) {
                traceWrapper = new TraceWrapper(httpServletRequest);
                traceWrapper.putHeader("X-B3-TraceId", traceId);
            }
        }
        try {
            chain.doFilter(Objects.nonNull(traceWrapper) ? traceWrapper : request, response);
        } catch (Exception e) {
            logger.warn("traceId Filter error{}", e.getMessage());
        }

    }
}
