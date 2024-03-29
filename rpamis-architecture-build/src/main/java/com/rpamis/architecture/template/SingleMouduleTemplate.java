package com.rpamis.architecture.template;

import com.rpamis.architecture.consts.TemplateTypeEnum;
import com.rpamis.architecture.pojo.FileVO;
import org.springframework.stereotype.Component;

/**
 * 单体架构项目
 *
 * @author benym
 * @date 2022/7/21 10:39 上午
 */
@Component
public class SingleMouduleTemplate extends AbstractBuildTemplate {

  @Override
  protected String getTemplateType() {
    return TemplateTypeEnum.SINGLE_MOUDULE.getCode();
  }

  @Override
  protected void initPath() {

  }

  @Override
  protected void resolve() {

  }

  @Override
  protected FileVO create() {
    return null;
  }
}
