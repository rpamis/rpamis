package com.rpamis.extension.spi;

/**
 * 原生Java spi加载策略
 *
 * @author benym
 * @date 2023/10/31 23:42
 */
public class NativeSpiLoadingStrategy implements SpiLoadingStrategy {

  @Override
  public String spiPath() {
    return "META-INF/services/";
  }

  @Override
  public String getName() {
    return "NATIVE";
  }

  @Override
  public int getPriority() {
    return MIN_PRIORITY;
  }
}
