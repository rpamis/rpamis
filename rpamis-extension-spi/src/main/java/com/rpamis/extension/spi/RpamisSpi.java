package com.rpamis.extension.spi;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Rpamis Spi注解
 *
 * @author benym
 * @date 2023/11/1 0:03
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface RpamisSpi {

  /**
   * Spi名称
   *
   * @return String
   */
  String value() default "";

}
