package com.rpamis.extension.spi;

import com.rpamis.extension.spi.loading.SpiLoadingStrategy;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.StreamSupport;

/**
 * Spi加载者
 *
 * @author benym
 * @date 2023/10/31 21:52
 */
public class SpiLoader<T> {

  private static final Logger LOGGER = Logger.getLogger(SpiLoader.class.getName());

  private static final Pattern NAME_SEPARATOR = Pattern.compile("\\s*[,]+\\s*");

  private String cachedDefaultName;

  private final Map<String, IllegalStateException> exceptions = new ConcurrentHashMap<>();

  private final Class<?> type;

  /**
   * 插件Ioc工厂
   */
  private final PluginInjector pluginInjector;

  /**
   * Spi加载策略数组
   */
  private static final SpiLoadingStrategy[] SPI_LOADING_STRATEGIES = initSpiLoadingStrategies();

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
   * Spi Class->Spi name
   */
  private final ConcurrentMap<Class<?>, String> cachedNames = new ConcurrentHashMap<>();


  /**
   * Spi name->Spi impl Class实例
   */
  private final UniversalContainer<Map<String, Class<?>>> cachedNameToSpiImplClasses = new UniversalContainer<>();

  /**
   * 适应者实例缓存
   */
  private final UniversalContainer<Object> cachedAccommodatorInstance = new UniversalContainer<>();

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
      SPI_LOADERS.putIfAbsent(type, new SpiLoader<>(type));
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

  private IllegalStateException findException(String name) {
    for (Map.Entry<String, IllegalStateException> entry : exceptions.entrySet()) {
      if (entry.getKey().toLowerCase().contains(name.toLowerCase())) {
        return entry.getValue();
      }
    }
    StringBuilder buf = new StringBuilder(
        "No such extension " + type.getName() + " by name " + name);
    int i = 1;
    for (Map.Entry<String, IllegalStateException> entry : exceptions.entrySet()) {
      if (i == 1) {
        buf.append(", possible causes: ");
      }
      buf.append("\r\n(");
      buf.append(i++);
      buf.append(") ");
      buf.append(entry.getKey());
      buf.append(":\r\n");
      buf.append(entry.getValue().toString());
    }
    return new IllegalStateException(buf.toString());
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
      throw findException(name);
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
    if (pluginInjector == null) {
      return;
    }
    try {
      // 获取spi实现类的所有method，找到spi实现类中依赖的其他通用为spi的实例，并进行依赖注入
      for (Method method : spiImplInstance.getClass().getMethods()) {
        if (!isSetter(method)) {
          continue;
        }
        Class<?> parameterType = method.getParameterTypes()[0];
        if (isPrimitives(parameterType)) {
          continue;
        }
        // 根据setter方法注入实体
        inject(parameterType, method, spiImplInstance);
      }
    } catch (Exception e) {
      String exceptionMessage = String.format("%s inject failed, because: %s", spiImplInstance, e);
      LOGGER.log(Level.SEVERE, exceptionMessage);
    }
  }

  /**
   * 依赖注入，让Spi实现类中依赖的其他Spi实现类通过setter方法注入
   *
   * @param parameterType   参数类型Class
   * @param method          对应方法
   * @param spiImplInstance spi实现类实例
   */
  public void inject(Class<?> parameterType, Method method, T spiImplInstance) {
    try {
      String setterPropertyName = this.getSetterProperty(method);
      // 根据参数类型如Protocol和属性名字如protocol获取应该注入的对象
      Object object = pluginInjector.getSpiImpl(parameterType, setterPropertyName);
      if (object != null) {
        // 能够走到这里，说明method为set方法的method，因为如果不是需要注入的spi实现类对象，上述object为null
        // 直接invoke，将object通过set赋值给需要依赖注入的spi实例
        method.invoke(spiImplInstance, object);
      }
    } catch (Exception e) {
      String exceptionMessage = String.format(
          "Failed to inject via method %s  of interface %s, because: %s", method.getName(),
          type.getName(), e);
      LOGGER.log(Level.SEVERE, exceptionMessage);
    }
  }

  /**
   * method是否是public、名称是否为set开头、是否只有一个参数
   *
   * @param method method
   * @return boolean
   */
  private boolean isSetter(Method method) {
    return method.getName().startsWith("set")
        && method.getParameterTypes().length == 1
        && Modifier.isPublic(method.getModifiers());
  }

  /**
   * 判断是否是包装类型 如果class是Array则获取数组的组件Class，比如String[] 获取的是String.class
   *
   * @param cls cls
   * @return boolean
   */
  private static boolean isPrimitives(Class<?> cls) {
    while (cls.isArray()) {
      cls = cls.getComponentType();
    }
    return isPrimitive(cls);
  }

  /**
   * 判断是否是包装类型
   *
   * @param cls cls
   * @return boolean
   */
  private static boolean isPrimitive(Class<?> cls) {
    return cls.isPrimitive() || cls == String.class || cls == Boolean.class
        || cls == Character.class
        || Number.class.isAssignableFrom(cls) || Date.class.isAssignableFrom(cls);
  }

  /**
   * 获取method setter方法的属性名称，比如setVersion，返回version 如果method setter方法的名称小于3，则返回""
   *
   * @param method method
   * @return String
   */
  private String getSetterProperty(Method method) {
    return method.getName().length() > 3 ? method.getName().substring(3, 4).toLowerCase()
        + method.getName().substring(4) : "";
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
   * 提取Spi注解上默认的Spi名称，并进行缓存
   */
  private void cacheDefaultSpiName() {
    final RpamisSpi annotation = type.getAnnotation(RpamisSpi.class);
    if (annotation == null) {
      return;
    }
    String value = annotation.value();
    if ((value = value.trim()).length() > 0) {
      String[] names = NAME_SEPARATOR.split(value);
      if (names.length > 1) {
        throw new IllegalStateException(
            "More than 1 default spi name on spi " + type.getName()
                + ": " + Arrays.toString(names));
      }
      if (names.length == 1) {
        cachedDefaultName = names[0];
      }
    }
  }

  /**
   * 根据Spi加载策略，加载Spi实现类Class进入缓存
   *
   * @return Map<String, Class < ?>>
   */
  private Map<String, Class<?>> loadSpiImplClasses() throws InterruptedException {
    cacheDefaultSpiName();
    Map<String, Class<?>> spiToImplClassMap = new HashMap<>(64);
    for (SpiLoadingStrategy strategy : SPI_LOADING_STRATEGIES) {
      loadSpiPath(spiToImplClassMap, strategy, type.getName());
    }
    return spiToImplClassMap;
  }


  /**
   * 根据Spi加载策略进行资源加载
   *
   * @param spiToImplClassMap     spi名称->spi实现Class缓存
   * @param strategy              加载策略
   * @param spiInterfaceReference spi接口名称
   */
  private void loadSpiPath(Map<String, Class<?>> spiToImplClassMap, SpiLoadingStrategy strategy,
      String spiInterfaceReference) {
    String fileName = strategy.spiPath() + spiInterfaceReference;
    try {
      Enumeration<URL> urls;
      ClassLoader classLoader = SpiLoader.class.getClassLoader();
      if (classLoader != null) {
        urls = classLoader.getResources(fileName);
      } else {
        urls = ClassLoader.getSystemResources(fileName);
      }
      if (urls != null) {
        while (urls.hasMoreElements()) {
          URL resourceUrl = urls.nextElement();
          loadResource(spiToImplClassMap, classLoader, resourceUrl);
        }
      }
    } catch (Throwable e) {
      LOGGER.log(Level.SEVERE, "Exception when load extension class(interface: " +
          type + ", description file: " + fileName + ").", e);
    }
  }

  /**
   * 从spi拓展文件中加载spi资源
   *
   * @param spiToImplClassMap spiToImplClassMap
   * @param classLoader       classLoader
   * @param resourceUrl       resourceUrl
   */
  private void loadResource(Map<String, Class<?>> spiToImplClassMap, ClassLoader classLoader,
      URL resourceUrl) {
    try {
      try (BufferedReader reader = new BufferedReader(
          new InputStreamReader(resourceUrl.openStream(),
              StandardCharsets.UTF_8))) {
        String line;
        while ((line = reader.readLine()) != null) {
          final int ci = line.indexOf('#');
          if (ci >= 0) {
            line = line.substring(0, ci);
          }
          line = line.trim();
          if (line.length() > 0) {
            try {
              String name = null;
              int i = line.indexOf('=');
              if (i > 0) {
                name = line.substring(0, i).trim();
                line = line.substring(i + 1).trim();
              }
              if (line.length() > 0) {
                loadClass(spiToImplClassMap, resourceUrl, Class.forName(line, true, classLoader),
                    name);
              }
            } catch (Throwable t) {
              IllegalStateException e = new IllegalStateException(
                  "Failed to load extension class(interface: " + type + ", class line: " + line
                      + ") in " + resourceUrl + ", cause: " + t.getMessage(), t);
              exceptions.put(line, e);
            }
          }
        }
      }
    } catch (Throwable e) {
      LOGGER.log(Level.SEVERE, "Exception when load extension class(interface: " +
          type + ", class file: " + resourceUrl + ") in " + resourceUrl, e);
    }
  }

  private void loadClass(Map<String, Class<?>> spiToImplClassMap, URL resourceUrl, Class<?> clazz,
      String name) {
    // 判断class是否实现了type类型的接口
    if (!type.isAssignableFrom(clazz)) {
      throw new IllegalStateException("Error when load extension class(interface: " +
          type + ", class line: " + clazz.getName() + "), class "
          + clazz.getName() + "is not subtype of interface.");
    }
    // 如果是适应者则跳过
    if (clazz.isAnnotationPresent(Accommodator.class)) {
      return;
    }
    // 如果没有配置扩展名，自动生成，主要用于兼容Java SPI的配置规范
    if (name == null || name.length() == 0) {
      name = findAnnotationName(clazz);
      if (name.length() == 0) {
        throw new IllegalStateException(
            "No such extension name for the class " + clazz.getName() + " in the config "
                + resourceUrl);
      }
    }
    String[] names = NAME_SEPARATOR.split(name);
    if (names != null && names.length > 0) {
      for (String n : names) {
        cacheName(clazz, n);
        saveInSpiToImplClassMap(spiToImplClassMap, clazz, n);
      }
    }
  }

  private void saveInSpiToImplClassMap(Map<String, Class<?>> spiToImplClassMap, Class<?> clazz,
      String name) {
    Class<?> spiImplClass = spiToImplClassMap.get(name);
    if (spiImplClass == null) {
      spiToImplClassMap.put(name, clazz);
    } else if (spiImplClass != clazz) {
      String duplicateMsg = "Duplicate extension " + type.getName() + " name " + name + " on "
          + spiImplClass.getName() + " and " + clazz.getName();
      LOGGER.info(duplicateMsg);
      throw new IllegalStateException(duplicateMsg);
    }
  }

  /**
   * 缓存Spi Class->Spi Name
   *
   * @param clazz clazz
   * @param name  name
   */
  private void cacheName(Class<?> clazz, String name) {
    if (!cachedNames.containsKey(clazz)) {
      cachedNames.put(clazz, name);
    }
  }

  private String findAnnotationName(Class<?> clazz) {
    RpamisSpi rpamisSpi = clazz.getAnnotation(RpamisSpi.class);
    if (rpamisSpi != null) {
      return rpamisSpi.value();
    }
    String name = clazz.getSimpleName();
    if (name.endsWith(type.getSimpleName())) {
      name = name.substring(0, name.length() - type.getSimpleName().length());
    }
    return name.toLowerCase();
  }

  /**
   * 获取自适应插件注入者
   *
   * @return PluginInjector
   */
  @SuppressWarnings("unchecked")
  public T getAccommodator() {
    Object instance = cachedAccommodatorInstance.getValue();
    if (instance == null) {
      synchronized (cachedAccommodatorInstance) {
        instance = cachedAccommodatorInstance.getValue();
        if (instance == null) {
          instance = new SelfAdaptivePluginInjector();
          cachedAccommodatorInstance.setValue(instance);
        }
      }
    }
    return (T) instance;
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
    return Arrays.asList(SPI_LOADING_STRATEGIES);
  }


}
