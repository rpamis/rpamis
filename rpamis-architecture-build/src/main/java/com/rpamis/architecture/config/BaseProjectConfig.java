package com.rpamis.architecture.config;

import com.rpamis.architecture.consts.TemplateTypeEnum;
import com.rpamis.architecture.pojo.Dependency;
import com.rpamis.architecture.pojo.Project;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import javax.validation.Valid;
import lombok.NoArgsConstructor;

/**
 * 项目基本配置
 *
 * @author benym
 * @date 2022/7/20 5:01 下午
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BaseProjectConfig implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * project
   */
  @Valid
  private Project project;

  /**
   * dependency
   */
  private Dependency dependency;

  /**
   * 模板类型
   */
  private TemplateTypeEnum templateType;
}
