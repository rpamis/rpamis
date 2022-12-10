package com.benym.rpas.architecture.template;

import com.benym.rpas.architecture.consts.TemplateType;
import com.benym.rpas.architecture.pojo.FileVO;
import org.springframework.stereotype.Component;

/**
 * starter项目
 *
 * @date 2022/7/21 10:37 上午
 */
@Component
public class StarterTemplate extends BuildAbstractTemplate {

    @Override
    protected String getTemplateType() {
        return TemplateType.STARTER.getCode();
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
