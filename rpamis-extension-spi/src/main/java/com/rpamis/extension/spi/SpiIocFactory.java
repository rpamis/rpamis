package com.rpamis.extension.spi;

/**
 * Spi实例工厂
 *
 * @author benym
 * @date 2023/11/8 18:44
 */
@RpamisSpi("spiFactory")
public interface SpiIocFactory {

  /**
   * 获取已实例化的Spi
   *
   * @param type type
   * @param name name
   * @return T
   * @param <T> T
   */
  <T> T getSpi(Class<T> type, String name);
}
