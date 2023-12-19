package com.rpamis.extension.spi;

/**
 * RpamisSpi插件注入者
 *
 * @author benym
 * @date 2023/11/8 18:46
 */
public class RpamisSpiPluginInjector implements PluginInjector {

  @Override
  @SuppressWarnings("unchecked")
  public <T> T getSpiImpl(Class<T> type, String name) {
    // 如果type是接口，且被spi注解标记则直接进行获取
    if (type.isInterface() && type.isAnnotationPresent(RpamisSpi.class)) {
      return SpiLoader.getSpiImplByClassAndName(type, name);
    } else {
      // 如果不是接口，且没有被spi注解标记，则尝试获取它实现的接口中被spi注解标记的接口
      Class<?> interfaceWithAnnotation = getInterfaceWithAnnotation(type);
      if (interfaceWithAnnotation != null) {
        return (T) SpiLoader.getSpiImplByClassAndName(interfaceWithAnnotation, name);
      }
    }
    return null;
  }

  /**
   * 获取clazz类被RpamisSpi注解标记具体接口类
   *
   * @param clazz clazz
   * @return Class<?>
   */
  private static Class<?> getInterfaceWithAnnotation(Class<?> clazz) {
    Class<?>[] interfaces = clazz.getInterfaces();
    for (Class<?> iface : interfaces) {
      if (iface.isAnnotationPresent(RpamisSpi.class)) {
        return iface;
      }
    }
    return null;
  }
}
