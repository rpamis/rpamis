package com.rpamis.architecture.consts;

/**
 * 项目类型枚举
 *
 * @author benym
 * @date 2022/7/21 11:03 上午
 */
public enum TemplateTypeEnum {

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

  private String code;

  private String desc;

  TemplateTypeEnum(String code, String desc) {
    this.code = code;
    this.desc = desc;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }
}
