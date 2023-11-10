package com.rpamis.extension.spi;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 自适应插件注入者
 *
 * @author benym
 * @date 2023/11/10 14:58
 */
@Accommodator("selfAdaptivePlugin")
public class SelfAdaptivePluginInjector implements PluginInjector {

  private final List<PluginInjector> pluginInjectors;

  public SelfAdaptivePluginInjector() {
    SpiLoader<PluginInjector> spiLoader = SpiLoader.getSpiLoader(PluginInjector.class);
    pluginInjectors = spiLoader.getSupportedSpiImpl().stream()
        .map(spiLoader::getSpiImpl)
        .collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
  }

  /**
   * 遍历所有插件注入者，根据Class Type和名称获取Spi实现类
   * 如果同名，返回第一个实现类
   *
   * @param type type
   * @param name name
   * @param <T>  T
   * @return T
   */
  @Override
  public <T> T getSpiImpl(Class<T> type, String name) {
    return pluginInjectors.stream()
        .map(pluginInjector -> pluginInjector.getSpiImpl(type, name))
        .filter(Objects::nonNull)
        .findFirst()
        .orElse(null);
  }
}
