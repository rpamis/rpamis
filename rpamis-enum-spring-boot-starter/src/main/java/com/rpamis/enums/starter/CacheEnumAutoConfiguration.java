package com.rpamis.enums.starter;

import com.rpamis.enums.core.EnumCacheBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 枚举自动加载
 *
 * @author benym
 * @date 2023/7/4 16:50
 */
@Configuration
@EnableConfigurationProperties(CacheEnumProperties.class)
@ConditionalOnProperty(prefix = CacheEnumProperties.PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
public class CacheEnumAutoConfiguration implements ApplicationRunner {

  @Autowired
  private CacheEnumProperties cacheEnumProperties;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    EnumCacheBuilder.setScanPackages(cacheEnumProperties.getPackages());
    EnumCacheBuilder.build();
  }
}
