package com.rpamis.extension.spi;

/**
 * 插件实例工厂
 *
 * @author benym
 * @date 2023/11/8 18:44
 */
@RpamisSpi
public interface PluginInjector {

  /**
   * 获取Spi实现类
   *
   * @param type type
   * @param name name
   * @return T
   * @param <T> T
   */
  <T> T getSpiImpl(Class<T> type, String name);
}
