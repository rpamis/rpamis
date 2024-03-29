package com.rpamis.exception.starter;

import com.rpamis.common.exception.exception.ExceptionBaseHandler;
import com.rpamis.common.exception.exception.ExceptionErrorHandler;
import com.rpamis.common.exception.properties.ExceptionProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 全局异常自动注入
 *
 * @author benym
 * @date 2023/2/15 21:40
 */
@Configuration
@EnableConfigurationProperties(value = ExceptionProperties.class)
@ConditionalOnProperty(prefix = ExceptionProperties.PREFIX, value = "enabled", havingValue = "true", matchIfMissing = true)
public class ExceptionAutoConfiguration {

  /**
   * 仅当@ConditionalOnProperty条件生效时注入
   *
   * @return ExceptionErrorHandler
   */
  @Bean
  @ConditionalOnMissingBean
  public ExceptionErrorHandler enableException() {
    return new ExceptionErrorHandler();
  }

  /**
   * 根据yml条件注入Exception.class的异常捕获
   *
   * @return ExceptionBaseHandler
   */
  @Bean
  @ConditionalOnMissingBean
  @ConditionalOnExpression("${rpamis.exception.include-exception-class:true}")
  public ExceptionBaseHandler enableBaseException() {
    return new ExceptionBaseHandler();
  }

  /**
   * 当yml配置情况下注入这个类，仅提供给dubbo filter使用 Bean name需和dubbo filter中该变量名一致，否则set注入失效
   *
   * @return ExceptionProperties
   */
  @Bean(name = "exceptionProperties")
  @ConditionalOnMissingBean
  public ExceptionProperties enableProperties() {
    return new ExceptionProperties();
  }
}
