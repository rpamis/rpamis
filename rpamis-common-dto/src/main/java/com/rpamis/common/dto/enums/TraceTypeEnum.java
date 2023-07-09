package com.rpamis.common.dto.enums;

import com.rpamis.enums.core.CachableEnum;

/**
 * Trace类型枚举
 *
 * @author benym
 * @date 2022/7/8 4:18 下午
 */
public enum TraceTypeEnum implements CachableEnum<String, String> {

  /**
   * rpamis默认实现
   */
  RPAMIS("rpamis", "rpamis默认实现"),
  /**
   * skywalking实现
   */
  SKYWALK("skywalking", "skywalking实现");

  /**
   * code
   */
  private String code;

  /**
   * desc
   */
  private String desc;

  TraceTypeEnum(String code, String desc) {
    this.code = code;
    this.desc = desc;
  }

  @Override
  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  @Override
  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }
}
