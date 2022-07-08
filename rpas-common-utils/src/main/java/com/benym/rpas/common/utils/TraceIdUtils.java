package com.benym.rpas.common.utils;

import com.benym.rpas.common.dto.enums.TraceType;
import org.apache.skywalking.apm.toolkit.trace.TraceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * @date 2022/7/8 4:04 下午
 */

public class TraceIdUtils {

    private static final Logger logger = LoggerFactory.getLogger(TraceIdUtils.class);

    public TraceIdUtils() {
    }

    public static String getTraceId(String traceType) {
        String traceId = null;
        try {
            if (TraceType.SKYWALK.getType().equals(traceType)) {
                traceId = TraceContext.traceId();
            } else {
                traceId = MDC.get("X-B3-TraceId");
            }
        } catch (Exception e) {
            logger.warn("获取traceId异常{}", e.getMessage());
        }
        return traceId;
    }
}
