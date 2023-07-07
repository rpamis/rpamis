package com.rpamis.enums.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

/**
 * Class扫描工具类
 *
 * @author benym
 * @date 2023/6/12 15:24
 */
public class ClassScanner {

  private ClassScanner() {
    throw new IllegalStateException("工具类禁止实例化");
  }

  /**
   * 获取实现某个接口的所有实现类
   *
   * @param interfaceClass 接口类
   * @param <T>            <T>
   * @return 所有实现类列表
   */
  public static <T> List<Class<? extends T>> getImplClass(Class<T> interfaceClass) {
    Reflections reflections = new Reflections(Scanners.SubTypes);
    Set<Class<? extends T>> classes = reflections.getSubTypesOf(interfaceClass);
    return new ArrayList<>(classes);
  }

  /**
   * 获取系统内所有实现了CacheableEnum接口的实现类
   *
   * @return List<Class < ? extends CachableEnum < ?, ?>>>
   */
  @SuppressWarnings("unchecked")
  public static List<Class<? extends CachableEnum<?, ?>>> getCachableEnumImplClasses() {
    Reflections reflections = new Reflections(Scanners.SubTypes);
    return reflections.getSubTypesOf(CachableEnum.class)
        .stream()
        .map(clazz -> (Class<? extends CachableEnum<?, ?>>) clazz)
        .distinct().collect(Collectors.toList());
  }

  /**
   * 获取配置路径下实现了CacheableEnum接口的实现类
   *
   * @return List<Class < ? extends CachableEnum < ?, ?>>>
   */
  @SuppressWarnings("unchecked")
  public static List<Class<? extends CachableEnum<?, ?>>> getCachableEnumImplClassesByPackages(
      String... paths) {
    Reflections reflections = new Reflections(paths, Scanners.SubTypes);
    return reflections.getSubTypesOf(CachableEnum.class)
        .stream()
        .map(clazz -> (Class<? extends CachableEnum<?, ?>>) clazz)
        .distinct().collect(Collectors.toList());
  }
}
