package com.rpamis.architecture.consts;

import com.rpamis.enums.core.CachableEnum;

/**
 * 项目类型枚举
 *
 * @author benym
 * @date 2022/7/21 11:03 上午
 */
public enum TemplateTypeEnum implements CachableEnum<String, String> {

  /**
   * 多模块项目
   */
  MULTI_MOUDULE("MULTI", "多模块项目"),
  /**
   * 单模块项目
   */
  SINGLE_MOUDULE("SINGLE", "单模块项目"),
  /**
   * Starter项目
   */
  STARTER("STARTER", "Starter项目");

  /**
   * code
   */
  private String code;

  /**
   * desc
   */
  private String desc;

  TemplateTypeEnum(String code, String desc) {
    this.code = code;
    this.desc = desc;
  }

  @Override
  public String getCode() {
    return code;
  }

  @Override
  public String getDesc() {
    return desc;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }
}
