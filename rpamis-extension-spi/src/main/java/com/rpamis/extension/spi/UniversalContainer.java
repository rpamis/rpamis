package com.rpamis.extension.spi;

/**
 * 通用实例容器
 *
 * @author benym
 * @date 2023/11/9 17:20
 */
public class UniversalContainer<T> {

  private volatile T value;

  public UniversalContainer() {
  }

  public T getValue() {
    return value;
  }

  public void setValue(T value) {
    this.value = value;
  }
}
