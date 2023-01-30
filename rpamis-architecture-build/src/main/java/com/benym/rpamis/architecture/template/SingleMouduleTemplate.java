package com.benym.rpamis.architecture.template;

import com.benym.rpamis.architecture.consts.TemplateType;
import com.benym.rpamis.architecture.pojo.FileVO;
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
        return TemplateType.SINGLE_MOUDULE.getCode();
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