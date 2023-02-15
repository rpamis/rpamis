package com.benym.rpamis.common.exception.autoconfigure;

import com.benym.rpamis.common.exception.exception.ExceptionErrorHandler;
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
@ConditionalOnProperty(prefix = ExceptionProperties.PREFIX, value = "enable", havingValue = "true")
public class ExceptionAutoConfiguration {

    /**
     * 仅当@ConditionalOnProperty条件生效时注入
     *
     * @return ExceptionErrorHandler
     */
    @Bean
    @ConditionalOnMissingBean
    public ExceptionErrorHandler enableException(){
        return new ExceptionErrorHandler();
    }
}
