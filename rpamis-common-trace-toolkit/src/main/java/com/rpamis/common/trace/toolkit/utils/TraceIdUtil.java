package com.rpamis.common.trace.toolkit.utils;

import com.rpamis.common.dto.trace.Trace;
import com.rpamis.common.trace.toolkit.strategy.TraceStrategy;
import com.rpamis.common.trace.toolkit.strategy.TraceStrategyFactory;
import com.rpamis.common.utils.SnowflakeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.NamedThreadLocal;
import org.springframework.stereotype.Component;

/**
 * TraceId工具类
 *
 * @author benym
 * @date 2022/7/8 4:04 下午
 */
@Component
public class TraceIdUtil {

  private static final Logger logger = LoggerFactory.getLogger(TraceIdUtil.class);

  public static final ThreadLocal<Trace> CURRENT_TRACE = new NamedThreadLocal<>(
      "Trace-ThreadLocal");

  @Value("${trace.type:rpamis}")
  private String traceType;

  @Autowired
  private TraceStrategyFactory traceStrategyFactory;

  /**
   * 获取Trace实体
   *
   * @return Trace
   */
  public Trace getTrace() {
    Trace trace = CURRENT_TRACE.get();
    try {
      if (trace == null) {
        TraceStrategy strategy = traceStrategyFactory.getStrategy(traceType);
        trace = strategy.generateTrace();
      } else {
        // 如果traceId不变，请求的spanId不一样，需要重新生成
        trace.setSpanId(generateId());
        MDC.put(Trace.SPAN_ID, trace.getSpanId());
      }
      CURRENT_TRACE.set(trace);
    } catch (Exception e) {
      logger.warn("获取traceId异常{}", e.getMessage());
    }
    return trace;
  }

  /**
   * 生成唯一Id
   *
   * @return String
   */
  public static String generateId() {
    return String.valueOf(SnowflakeUtil.get().next());
  }

  /**
   * 设置Trace实体
   *
   * @param traceId traceId
   */
  public static void setTrace(String traceId) {
    if (traceId == null) {
      traceId = generateId();
    }
    Trace trace = new Trace();
    trace.setTraceId(traceId);
    trace.setSpanId(generateId());
    MDC.put(Trace.TRACE_ID, trace.getTraceId());
    MDC.put(Trace.SPAN_ID, trace.getSpanId());
    CURRENT_TRACE.set(trace);
  }

  public static void clearTrace() {
    CURRENT_TRACE.remove();
  }
}
