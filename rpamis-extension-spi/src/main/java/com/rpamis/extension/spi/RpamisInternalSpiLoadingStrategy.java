package com.rpamis.extension.spi;

/**
 * Rpamis内部Spi加载策略
 *
 * @author benym
 * @date 2023/10/31 23:35
 */
public class RpamisInternalSpiLoadingStrategy implements SpiLoadingStrategy{

  @Override
  public String spiPath() {
    return "META-INF/rpamis/internal/";
  }

  @Override
  public String getName() {
    return "RPAMIS_INTERNAL";
  }

  @Override
  public int getPriority() {
    return MAX_PRIORITY;
  }
}
