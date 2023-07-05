package com.rpamis.common.trace.toolkit.strategy;

import com.rpamis.common.dto.trace.Trace;
import com.rpamis.common.utils.SnowflakeUtil;

/**
 * 追踪实现策略
 *
 * @author benym
 * @date 2023/7/5 10:30
 */
public interface TraceStrategy {

  /**
   * 生成Trace实体
   *
   * @return Trace
   */
  Trace generateTrace();

  /**
   * 生成Id
   *
   * @return String
   */
  default String generateId(){
    return String.valueOf(SnowflakeUtil.get().next());
  }

  /**
   * 策略名称
   *
   * @return String
   */
  String strategyName();
}
