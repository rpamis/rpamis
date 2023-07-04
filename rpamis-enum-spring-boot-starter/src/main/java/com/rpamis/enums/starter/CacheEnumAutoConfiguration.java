package com.rpamis.enums.starter;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

/**
 * 枚举自动加载
 *
 * @author benym
 * @date 2023/7/4 16:50
 */
@Configuration
public class CacheEnumAutoConfiguration implements ApplicationRunner {

  @Override
  public void run(ApplicationArguments args) throws Exception {
    Class.forName("com.rpamis.common.utils.EnumLookup");
  }
}
