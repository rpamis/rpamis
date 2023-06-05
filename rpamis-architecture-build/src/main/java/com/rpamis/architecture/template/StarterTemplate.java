package com.rpamis.architecture.template;

import com.rpamis.architecture.consts.TemplateTypeEnum;
import com.rpamis.architecture.pojo.FileVO;
import org.springframework.stereotype.Component;

/**
 * starter项目
 *
 * @author benym
 * @date 2022/7/21 10:37 上午
 */
@Component
public class StarterTemplate extends AbstractBuildTemplate {

    @Override
    protected String getTemplateType() {
        return TemplateTypeEnum.STARTER.getCode();
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
