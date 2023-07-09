package com.rpamis.common.trace.toolkit.strategy.impl;

import com.rpamis.common.dto.enums.TraceTypeEnum;
import com.rpamis.common.dto.trace.Trace;
import com.rpamis.common.trace.toolkit.strategy.TraceStrategy;
import org.apache.skywalking.apm.toolkit.trace.TraceContext;
import org.springframework.stereotype.Component;

/**
 * Skywalking Trace策略实现
 *
 * @author benym
 * @date 2023/7/5 10:44
 */
@Component
public class SkyWalkingTraceStrategyImpl implements TraceStrategy {

  @Override
  public Trace generateTrace() {
    String traceId = TraceContext.traceId();
    String spanId = String.valueOf(TraceContext.spanId());
    Trace trace = new Trace();
    trace.setTraceId(traceId);
    trace.setSpanId(spanId);
    return trace;
  }

  @Override
  public String strategyName() {
    return TraceTypeEnum.SKYWALK.getCode();
  }
}
