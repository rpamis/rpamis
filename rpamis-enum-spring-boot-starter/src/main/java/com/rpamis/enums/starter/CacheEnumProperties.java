package com.rpamis.enums.starter;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 枚举缓存配置类
 *
 * @author benym
 * @date 2023/7/7 17:53
 */
@ConfigurationProperties(prefix = CacheEnumProperties.PREFIX)
public class CacheEnumProperties {

  public static final String PREFIX = "cache.enums";

  /**
   * 是否启用枚举缓存
   */
  private Boolean enabled = true;

  /**
   * 基本扫描路径
   */
  private List<String> packages;


  public List<String> getPackages() {
    return packages;
  }

  public void setPackages(List<String> packages) {
    this.packages = packages;
  }

  public Boolean getEnabled() {
    return enabled;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }
}
