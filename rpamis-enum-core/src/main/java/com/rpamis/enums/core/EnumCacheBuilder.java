package com.rpamis.enums.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 枚举缓存构建器
 *
 * @author benym
 * @date 2023/7/7 18:35
 */
public class EnumCacheBuilder {

  private EnumCacheBuilder() {
    throw new IllegalStateException("工具类禁止实例化");
  }

  /**
   * code缓存
   */
  private static final Map<Class<? extends CachableEnum<?, ?>>, Map<Object, CachableEnum<?, ?>>> CODE_CACHE = new ConcurrentHashMap<>();

  /**
   * desc缓存
   */
  private static final Map<Class<? extends CachableEnum<?, ?>>, Map<Object, CachableEnum<?, ?>>> DESC_CACHE = new ConcurrentHashMap<>();

  /**
   * 扫描路径
   */
  private static List<String> scanPackages = new ArrayList<>();

  /**
   * 是否初始化标志位
   */
  private static volatile boolean initialized = false;

  /**
   * 初始化
   */
  public static void build() {
    if (!initialized) {
      synchronized (EnumCacheBuilder.class) {
        if (!initialized) {
          cacheEnums();
          initialized = true;
        }
      }
    }
  }

  /**
   * 缓存枚举方法
   */
  private static void cacheEnums() {
    if (scanPackages.isEmpty()) {
      List<Class<? extends CachableEnum<?, ?>>> implClasses = ClassScanner.getCachableEnumImplClasses();
      for (Class<? extends CachableEnum<?, ?>> implClass : implClasses) {
        cacheEnumClass(implClass);
      }
    } else {
      List<Class<? extends CachableEnum<?, ?>>> implClasses = ClassScanner.getCachableEnumImplClassesByPackages(
          scanPackages.toArray(new String[0]));
      for (Class<? extends CachableEnum<?, ?>> implClass : implClasses) {
        cacheEnumClass(implClass);
      }
    }
  }

  /**
   * 缓存枚举到Cache Map 当调用getCode()和getDesc()方法时，实际上返回的是CachableEnum的C和D类型的对象。
   * 由于泛型擦除的原因，在运行时无法获取具体的泛型类型参数，因此编译器将它们擦除为Object类型。
   * 尽管code和desc被声明为 Object类型，但由于C和D的实际类型是在实现CachableEnum接口时确定的
   * 因此调用getCode()和getDesc()方法返回的对象类型将与C和D的实际类型一致。
   *
   * @param implClass 实现CachableEnum接口的实现类
   */
  private static void cacheEnumClass(Class<? extends CachableEnum<?, ?>> implClass) {
    Map<Object, CachableEnum<?, ?>> codeMap = new HashMap<>(8);
    Map<Object, CachableEnum<?, ?>> descMap = new HashMap<>(8);
    CachableEnum<?, ?>[] enumConstants = implClass.getEnumConstants();
    for (CachableEnum<?, ?> cachableEnum : enumConstants) {
      Object code = cachableEnum.getCode();
      Object desc = cachableEnum.getDesc();
      codeMap.put(code, cachableEnum);
      descMap.put(desc, cachableEnum);
    }
    CODE_CACHE.put(implClass, codeMap);
    DESC_CACHE.put(implClass, descMap);
  }

  @SuppressWarnings("all")
  public static Map<Class<? extends CachableEnum<?, ?>>, Map<Object, CachableEnum<?, ?>>> getCodeCache() {
    build();
    return CODE_CACHE;
  }

  @SuppressWarnings("all")
  public static Map<Class<? extends CachableEnum<?, ?>>, Map<Object, CachableEnum<?, ?>>> getDescCache() {
    build();
    return DESC_CACHE;
  }

  public static void setScanPackages(List<String> scanPackages) {
    EnumCacheBuilder.scanPackages = scanPackages;
  }
}
