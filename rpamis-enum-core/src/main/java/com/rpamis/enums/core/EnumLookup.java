package com.rpamis.enums.core;

import java.util.HashMap;

/**
 * 枚举缓存工具类，powered by chatgpt
 *
 * @author benym
 * @date 2023/6/12 11:31
 */
public class EnumLookup {

  private EnumLookup() {
    throw new IllegalStateException("工具类禁止实例化");
  }

  /**
   * 通过枚举class和code获取枚举
   *
   * @param enumClass 枚举class
   * @param code      code
   * @return {@link T}
   */
  public static <T extends CachableEnum<?, ?>> T getEnumByCode(Class<T> enumClass, Object code) {
    return enumClass.cast(
        EnumCacheBuilder.getCodeCache().getOrDefault(enumClass, new HashMap<>(8)).get(code));
  }

  /**
   * 通过枚举class和desc获取枚举
   *
   * @param enumClass 枚举class
   * @param desc      desc
   * @return {@link T}
   */
  public static <T extends CachableEnum<?, ?>> T getEnumByDesc(Class<T> enumClass, Object desc) {
    return enumClass.cast(
        EnumCacheBuilder.getDescCache().getOrDefault(enumClass, new HashMap<>(8)).get(desc));
  }
}
