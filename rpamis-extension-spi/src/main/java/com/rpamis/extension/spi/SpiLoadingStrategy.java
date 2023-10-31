package com.rpamis.extension.spi;

/**
 * Spi加载策略接口
 *
 * @author benym
 * @date 2023/10/31 22:49
 */
public interface SpiLoadingStrategy extends LoadPriority {

  /**
   * spi加载路径
   *
   * @return String
   */
  String spiPath();

  /**
   * 获得类名
   *
   * @return String
   */
  default String getName() {
    return this.getClass().getSimpleName();
  }
}
