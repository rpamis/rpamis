package com.rpamis.extension.spi;

import java.util.Arrays;
import java.util.List;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.StreamSupport;

/**
 * Spi加载者
 *
 * @author benym
 * @date 2023/10/31 21:52
 */
public class SpiLoader<T> {

  private final Class<?> type;

  private final SpiIocFactory objectFactory;

  private static volatile SpiLoadingStrategy[] spiLoadingStrategies = initSpiLoadingStrategies();

  private static final ConcurrentHashMap<Class<?>, SpiLoader<?>> SPI_LOADERS = new ConcurrentHashMap<>(
      64);

  private SpiLoader(Class<?> type) {
    this.type = type;
    this.objectFactory =
        type == SpiIocFactory.class ? null : (SpiIocFactory) getSpiLoader(SpiIocFactory.class);
  }

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

  @SuppressWarnings("unchecked")
  public static <T> SpiLoader<T> getSpiLoader(Class<T> type) {
    if (type == null) {
      throw new IllegalArgumentException("Spi type == null");
    }
    if (!type.isInterface()) {
      throw new IllegalArgumentException("Spi type (" + type + ") is not an interface!");
    }
    if (!withSpiAnnotation(type)) {
      throw new IllegalArgumentException("Spi type (" + type +
          ") is not an spi, because it is NOT annotated with @" + RpamisSpi.class.getSimpleName()
          + "!");
    }
    SpiLoader<T> spiLoader = (SpiLoader<T>) SPI_LOADERS.get(type);
    if (spiLoader == null) {
      SPI_LOADERS.putIfAbsent(type, new SpiLoader<T>(type));
      spiLoader = (SpiLoader<T>) SPI_LOADERS.get(type);
    }
    return spiLoader;
  }

  /**
   * 是否被Spi注释标记
   *
   * @param type type
   * @return boolean
   */
  private static boolean withSpiAnnotation(Class<?> type) {
    return type.isAnnotationPresent(RpamisSpi.class);
  }

  /**
   * 获取所有已加载的Spi策略
   *
   * @return List<SpiLoadingStrategy>
   */
  public static List<SpiLoadingStrategy> getLoadedStrategies() {
    return Arrays.asList(spiLoadingStrategies);
  }


}
