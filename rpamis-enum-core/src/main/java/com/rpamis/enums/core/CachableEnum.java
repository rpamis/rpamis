package com.rpamis.enums.core;

/**
 * 通用缓存枚举接口
 *
 * @author benym
 * @date 2023/6/12 14:01
 */
public interface CachableEnum<C, D> {

  /**
   * 获取Code
   *
   * @return {@link Object}
   */
  C getCode();

  /**
   * 获取Desc
   *
   * @return {@link Object}
   */
  D getDesc();
}
