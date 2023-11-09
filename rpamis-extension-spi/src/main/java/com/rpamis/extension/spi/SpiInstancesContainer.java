package com.rpamis.extension.spi;

/**
 * Spi示例容器
 *
 * @author benym
 * @date 2023/11/9 17:20
 */
public class SpiInstancesContainer<T> {

  private volatile T value;

  public SpiInstancesContainer() {
  }

  public T getValue() {
    return value;
  }

  public void setValue(T value) {
    this.value = value;
  }
}
