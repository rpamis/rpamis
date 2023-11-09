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

  /**
   * Spi Ioc工厂
   */
  private final SpiIocFactory objectFactory;

  /**
   * Spi加载策略数组
   */
  private static volatile SpiLoadingStrategy[] spiLoadingStrategies = initSpiLoadingStrategies();

  /**
   * SpiLoader缓存，Spi接口->SpiLoader
   */
  private static final ConcurrentHashMap<Class<?>, SpiLoader<?>> SPI_LOADERS = new ConcurrentHashMap<>(
      64);

  /**
   *
   */
  private final ConcurrentHashMap<String, SpiInstancesContainer<Object>> cachedSpiInstances = new ConcurrentHashMap<>(
      64);

  private SpiLoader(Class<?> type) {
    this.type = type;
    this.objectFactory =
        type == SpiIocFactory.class ? null : getSpiLoader(SpiIocFactory.class).getSpiImplByName("rpamisSpiIocFactory");
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
   * 根据名称获取Spi实现类
   *
   * @param name name
   * @return T
   */
  @SuppressWarnings("unchecked")
  public T getSpiImplByName(String name) {
    if (name == null || "".equals(name)) {
      throw new IllegalArgumentException("Spi Impl name == null");
    }
    SpiInstancesContainer<Object> container = this.cachedSpiInstances.get(name);
    if (container == null) {
      this.cachedSpiInstances.putIfAbsent(name, new SpiInstancesContainer<>());
      container = this.cachedSpiInstances.get(name);
    }
    Object instance = container.getValue();
    if (instance == null) {
      synchronized (container) {
        instance = container.getValue();
        if (instance == null) {
          instance = this.createSpiImpl(name);
          container.setValue(instance);
        }
      }
    }
    return (T) instance;
  }

  private T createSpiImpl(String name) {
    return null;
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
