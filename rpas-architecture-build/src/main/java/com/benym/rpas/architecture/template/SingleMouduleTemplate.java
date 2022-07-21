package com.benym.rpas.architecture.template;

import com.benym.rpas.architecture.config.BaseProjectConfig;
import com.benym.rpas.architecture.consts.TemplateType;
import com.benym.rpas.architecture.pojo.FileVO;
import org.springframework.stereotype.Component;

/**
 * 单体架构项目
 *
 * @date 2022/7/21 10:39 上午
 */
@Component
public class SingleMouduleTemplate extends BuildAbstractTemplate {



    @Override
    public void run(String... args) throws Exception {
        TemplateFactory.register(TemplateType.SINGLE_MOUDULE, this);
    }

    @Override
    protected void initPath(BaseProjectConfig baseProjectConfig) {

    }

    @Override
    protected void resolve(BaseProjectConfig baseProjectConfig) {

    }

    @Override
    protected FileVO create(BaseProjectConfig baseProjectConfig) {
        return null;
    }
}
