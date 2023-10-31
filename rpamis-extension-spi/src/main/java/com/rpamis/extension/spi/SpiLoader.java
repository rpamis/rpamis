package com.rpamis.extension.spi;

import java.util.ServiceLoader;
import java.util.stream.StreamSupport;

/**
 * Spi加载者
 *
 * @author benym
 * @date 2023/10/31 21:52
 */
public class SpiLoader {

  private static volatile SpiLoadingStrategy[] spiLoadingStrategies = initSpiLoadingStrategies();

  /**
   * 初始化所有Spi加载策略
   *
   * @return SpiLoadingStrategy[]
   */
  private static SpiLoadingStrategy[] initSpiLoadingStrategies() {
    return StreamSupport.stream(ServiceLoader.load(SpiLoadingStrategy.class).spliterator(), false)
        .sorted()
        .toArray(SpiLoadingStrategy[]::new);
  }


}
