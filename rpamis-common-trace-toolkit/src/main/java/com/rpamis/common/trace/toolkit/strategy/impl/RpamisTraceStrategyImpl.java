package com.rpamis.common.trace.toolkit.strategy.impl;

import com.rpamis.common.dto.enums.TraceTypeEnum;
import com.rpamis.common.dto.trace.Trace;
import com.rpamis.common.trace.toolkit.strategy.TraceStrategy;
import org.slf4j.MDC;

/**
 * Rpamis Trace策略实现
 *
 * @author benym
 * @date 2023/7/5 10:45
 */
public class RpamisTraceStrategyImpl implements TraceStrategy {

  @Override
  public Trace generateTrace() {
    String traceId = MDC.get(Trace.TRACE_ID);
    String spanId = MDC.get(Trace.SPAN_ID);
    // 如果traceId为空，则同步生成spanId
    if (traceId == null) {
      traceId = generateId();
      spanId = generateId();
      MDC.put(Trace.TRACE_ID, traceId);
      MDC.put(Trace.SPAN_ID, spanId);
    }
    Trace trace = new Trace();
    trace.setTraceId(traceId);
    trace.setSpanId(spanId);
    return trace;
  }

  @Override
  public String strategyName() {
    return TraceTypeEnum.RPAMIS.getCode();
  }
}
