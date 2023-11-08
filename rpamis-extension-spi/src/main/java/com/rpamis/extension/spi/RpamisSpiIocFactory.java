package com.rpamis.extension.spi;

/**
 * RpamisSpi实例工厂
 *
 * @author benym
 * @date 2023/11/8 18:46
 */
public class RpamisSpiIocFactory implements SpiIocFactory {

  @Override
  public <T> T getSpi(Class<T> type, String name) {
    return null;
  }
}
