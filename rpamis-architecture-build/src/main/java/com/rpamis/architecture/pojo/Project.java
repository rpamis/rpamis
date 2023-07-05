package com.rpamis.architecture.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 项目基本信息实体
 *
 * @author benym
 * @date 2022/7/20 4:47 下午
 */
@Data
@Builder
public class Project {

  /**
   * groupId
   */
  @NotBlank(message = "groupId不能为空")
  @ApiModelProperty("groupId")
  private String groupId;

  /**
   * artifactId
   */
  @NotBlank(message = "artifactId不能为空")
  @ApiModelProperty("artifactId")
  @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9-]*[a-zA-Z]$", message = "artifactId仅能以英文字母开头, 英文字母结尾, 可包含字母、数字、-线")
  private String artifactId;

  /**
   * type
   */
  @ApiModelProperty("maven")
  private String type;

  /**
   * packaging
   */
  @ApiModelProperty("jar or war")
  private String packaging;

  /**
   * javaVersion
   */
  @ApiModelProperty("Java版本")
  private String javaVersion;

  /**
   * 项目版本
   */
  @ApiModelProperty("项目版本")
  private String version;

  /**
   * packageName
   */
  @NotBlank(message = "packageName不能为空")
  @ApiModelProperty("package")
  private String packageName;

  /**
   * 描述
   */
  @ApiModelProperty("项目描述")
  private String description;

  /**
   * mainName
   */
  @ApiModelProperty("主类名(由artifactId自动转化生成)")
  private String mainName;
}
