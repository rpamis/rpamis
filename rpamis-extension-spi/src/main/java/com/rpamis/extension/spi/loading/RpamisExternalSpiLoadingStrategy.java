package com.rpamis.extension.spi.loading;

/**
 * Rpamis外部Spi加载策略
 *
 * @author benym
 * @date 2023/10/31 23:39
 */
public class RpamisExternalSpiLoadingStrategy implements SpiLoadingStrategy {

  @Override
  public String spiPath() {
    return "META-INF/rpamis/";
  }

  @Override
  public String getName() {
    return "RPAMIS_EXTERNAL";
  }

  @Override
  public int getPriority() {
    return DEFAULT_PRIORITY;
  }
}
