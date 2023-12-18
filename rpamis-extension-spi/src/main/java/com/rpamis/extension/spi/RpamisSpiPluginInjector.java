package com.rpamis.extension.spi;

/**
 * RpamisSpi插件注入者
 *
 * @author benym
 * @date 2023/11/8 18:46
 */
public class RpamisSpiPluginInjector implements PluginInjector {

  @Override
  public <T> T getSpiImpl(Class<T> type, String name) {
    if (type.isInterface() && type.isAnnotationPresent(RpamisSpi.class)) {
      SpiLoader<T> spiLoader = SpiLoader.getSpiLoader(type);
      if (!spiLoader.getSupportedSpiImpl().isEmpty()) {
        return spiLoader.getSpiImpl(name);
      }
    }
    return null;
  }
}
