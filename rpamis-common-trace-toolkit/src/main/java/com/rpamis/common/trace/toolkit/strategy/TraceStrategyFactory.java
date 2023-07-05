package com.rpamis.common.trace.toolkit.strategy;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Trace策略工厂
 *
 * @author benym
 * @date 2023/7/5 10:50
 */
@Component
public class TraceStrategyFactory implements InitializingBean {

  @Autowired
  private ApplicationContext applicationContext;

  private Map<String, TraceStrategy> strategyMap = new HashMap<>(8);

  public TraceStrategy getStrategy(String traceType) {
    return strategyMap.get(traceType);
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    Map<String, TraceStrategy> beansOfType = applicationContext.getBeansOfType(TraceStrategy.class);
    strategyMap = Optional.of(beansOfType)
        .map(beansOfTypeMap -> beansOfTypeMap.values().stream()
            .filter(traceStrategy -> traceStrategy.strategyName() != null)
            .collect(Collectors.toMap(TraceStrategy::strategyName, Function.identity())))
        .orElse(new HashMap<>(8));
  }
}
