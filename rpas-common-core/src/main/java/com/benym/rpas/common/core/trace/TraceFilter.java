package com.benym.rpas.common.core.trace;

import cn.hutool.json.JSONUtil;
import com.benym.rpas.common.core.logger.LoggerHttp;
import com.benym.rpas.common.dto.enums.Trace;
import com.benym.rpas.common.dto.request.RequestLog;
import com.benym.rpas.common.utils.SnowflakeUtils;
import com.benym.rpas.common.utils.TraceIdUtils;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
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


    @Value("${trace.log.activate:true}")
    private static boolean traceLogActivate;

    public TraceFilter() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        Trace trace = TraceIdUtils.getTrace();
        TraceRequestWrapper traceRequestWrapper;
        RequestLog requestLog = null;
        TraceResponseWrapper traceResponseWrapper = new TraceResponseWrapper((HttpServletResponse) response);
        traceRequestWrapper = new TraceRequestWrapper((HttpServletRequest) request);
        if (traceLogActivate) {
            requestLog = LoggerHttp.init(traceRequestWrapper);
        }
        // 初始化入参信息
        traceRequestWrapper.putHeader(Trace.TRACE_ID, trace.getTraceId());
        traceRequestWrapper.putHeader(Trace.SPAN_ID, trace.getSpanId());
        try {
            chain.doFilter(traceRequestWrapper, response);
            // doFilter之后表示当前请求结束，下一次属于另外一个Span
            MDC.put(Trace.SPAN_ID, String.valueOf(SnowflakeUtils.get().next()));
            // 请求完成之后同步清理traceId
            TraceIdUtils.clearTrace();
            if (traceLogActivate) {
                // 更新出参信息
                LoggerHttp.update(requestLog, traceResponseWrapper);
                // 打印请求日志，包括入参、出参
                logger.info(JSONUtil.toJsonStr(requestLog));
            }
        } catch (Exception e) {
            logger.warn("traceId Filter error{}", e.getMessage());
        }

    }
}
