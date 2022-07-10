package com.benym.rpas.common.utils;

import com.benym.rpas.common.dto.enums.Trace;
import com.benym.rpas.common.dto.enums.TraceType;
import org.apache.skywalking.apm.toolkit.trace.TraceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.NamedThreadLocal;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @date 2022/7/8 4:04 下午
 */
@Component
public class TraceIdUtils {

    private static final Logger logger = LoggerFactory.getLogger(TraceIdUtils.class);

    public static final ThreadLocal<Trace> CURRENT_TRACE = new NamedThreadLocal<>("Trace-ThreadLocal");

    @Value("${trace.type:rpas}")
    private static String traceType;

    public TraceIdUtils() {
    }

    public static Trace getTrace() {
        String traceId;
        String spanId;
        Trace trace = CURRENT_TRACE.get();
        try {
            if (trace == null) {
                trace = new Trace();
                if (TraceType.SKYWALK.getType().equals(traceType)) {
                    traceId = TraceContext.traceId();
                    spanId = String.valueOf(TraceContext.spanId());
                } else {
                    traceId = MDC.get(Trace.TRACE_ID);
                    spanId = MDC.get(Trace.SPAN_ID);
                }
                // 如果traceId为空，则同步生成spanId
                if (traceId == null) {
                    traceId = UUID.randomUUID().toString();
                    spanId = String.valueOf(SnowflakeUtils.get().next());
                    MDC.put(Trace.TRACE_ID, traceId);
                    MDC.put(Trace.SPAN_ID, spanId);
                }
                trace.setTraceId(traceId);
                trace.setSpanId(spanId);
            } else {
                // 如果traceId不变，请求的spanId不一样，需要重新生成
                trace.setSpanId(String.valueOf(SnowflakeUtils.get().next()));
                MDC.put(Trace.SPAN_ID, trace.getSpanId());
            }
            CURRENT_TRACE.set(trace);
        } catch (Exception e) {
            logger.warn("获取traceId异常{}", e.getMessage());
        }
        return trace;
    }

    public static void setTrace(String traceId) {
        if (traceId == null) {
            traceId = UUID.randomUUID().toString();
        }
        Trace trace = new Trace();
        trace.setTraceId(traceId);
        trace.setSpanId(String.valueOf(SnowflakeUtils.get().next()));
        MDC.put(Trace.TRACE_ID, trace.getTraceId());
        MDC.put(Trace.SPAN_ID, trace.getSpanId());
        CURRENT_TRACE.set(trace);
    }

    public static void clearTrace() {
        CURRENT_TRACE.set(null);
        CURRENT_TRACE.remove();
    }
}
