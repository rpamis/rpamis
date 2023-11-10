package com.rpamis.extension.spi;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 适应者注解
 *
 * @author benym
 * @date 2023/11/10 16:26
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Accommodator {

  /**
   * 适应者名称
   *
   * @return String
   */
  String value() default "";
}
