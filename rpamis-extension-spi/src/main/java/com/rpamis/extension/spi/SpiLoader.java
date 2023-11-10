package com.rpamis.extension.spi;

import com.rpamis.extension.spi.loading.SpiLoadingStrategy;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.TreeSet;
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
   * 插件Ioc工厂
   */
  private final PluginInjector pluginInjector;

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
   * Spi name->Spi impl实例
   */
  private final ConcurrentHashMap<String, UniversalContainer<Object>> cachedSpiInstances = new ConcurrentHashMap<>(
      64);

  /**
   * Spi Class->Spi impl实例
   */
  private final ConcurrentHashMap<Class<?>, Object> cachedClassToSpiImpl = new ConcurrentHashMap<>(
      64);


  /**
   * Spi name->Spi impl Class实例
   */
  private final UniversalContainer<Map<String, Class<?>>> cachedNameToSpiImplClasses = new UniversalContainer<>();

  private SpiLoader(Class<?> type) {
    this.type = type;
    this.pluginInjector =
        type == PluginInjector.class ? null : getSpiLoader(PluginInjector.class).getAccommodator();
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
  public T getSpiImpl(String name) {
    if (name == null || "".equals(name)) {
      throw new IllegalArgumentException("Spi Impl name == null");
    }
    UniversalContainer<Object> container = this.cachedSpiInstances.get(name);
    if (container == null) {
      this.cachedSpiInstances.putIfAbsent(name, new UniversalContainer<>());
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

  /**
   * 创建Spi实现类实例
   *
   * @param name spi实现类名称
   * @return T
   */
  @SuppressWarnings("unchecked")
  private T createSpiImpl(String name) {
    Class<?> clazz = this.getSpiImplClasses().get(name);
    if (clazz == null) {
      throw new IllegalStateException("No such Spi implement " + type.getName() + "by name" + name);
    }
    try {
      T spiImplInstance = (T) cachedClassToSpiImpl.get(clazz);
      if (spiImplInstance == null) {
        cachedClassToSpiImpl.putIfAbsent(clazz, clazz.newInstance());
        spiImplInstance = (T) cachedClassToSpiImpl.get(clazz);
        injectSpiImpl(spiImplInstance);
      }
      return spiImplInstance;
    } catch (Throwable e) {
      throw new IllegalStateException("Extension instance (name: " + name + ", class: " +
          type + ") couldn't be instantiated: " + e.getMessage(), e);
    }
  }

  /**
   * 注入Spi实现类
   *
   * @param spiImplInstance spiImplInstance
   */
  private void injectSpiImpl(T spiImplInstance) {

  }

  /**
   * 获取支持的Spi实现类名称Set
   *
   * @return Set<String>
   */
  public Set<String> getSupportedSpiImpl() {
    Map<String, Class<?>> classes = getSpiImplClasses();
    return Collections.unmodifiableSet(new TreeSet<>(classes.keySet()));
  }

  /**
   * 获取Spi名称->Spi实现类Class的缓存
   *
   * @return Map<String, Class < ?>>
   */
  private Map<String, Class<?>> getSpiImplClasses() {
    Map<String, Class<?>> spiToImplClassMap = cachedNameToSpiImplClasses.getValue();
    if (spiToImplClassMap == null) {
      synchronized (cachedNameToSpiImplClasses) {
        spiToImplClassMap = cachedNameToSpiImplClasses.getValue();
        if (spiToImplClassMap == null) {
          try {
            spiToImplClassMap = loadSpiImplClasses();
          } catch (InterruptedException e) {
            throw new IllegalStateException(
                "Exception occurred when loading spi implement class (interface: " + type + ")", e);
          }
          cachedNameToSpiImplClasses.setValue(spiToImplClassMap);
        }
      }
    }
    return spiToImplClassMap;
  }

  /**
   * 根据Spi加载策略，加载Spi实现类Class进入缓存
   *
   * @return Map<String, Class < ?>>
   */
  private Map<String, Class<?>> loadSpiImplClasses() throws InterruptedException {
    Map<String, Class<?>> spiToImplClassMap = new HashMap<>(64);
    for (SpiLoadingStrategy strategy : spiLoadingStrategies) {
      loadSpiPath(spiToImplClassMap, strategy, type.getName());
    }
    return spiToImplClassMap;
  }


  private void loadSpiPath(Map<String, Class<?>> spiToImplClassMap, SpiLoadingStrategy strategy,
      String spiInterfaceReference) {
    String fileName = strategy.spiPath() + spiInterfaceReference;

  }

  /**
   * 获取自适应插件注入者
   *
   * @return PluginInjector
   */
  @SuppressWarnings("unchecked")
  public T getAccommodator() {
    return (T) new SelfAdaptivePluginInjector();
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
