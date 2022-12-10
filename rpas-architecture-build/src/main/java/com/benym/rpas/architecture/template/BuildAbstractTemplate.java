package com.benym.rpas.architecture.template;

import com.benym.rpas.architecture.config.BaseProjectConfig;
import com.benym.rpas.architecture.pojo.FileVO;
import com.benym.rpas.architecture.service.BuildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.Map;

/**
 * 模板方法
 *
 * @date 2022/7/21 10:19 上午
 */
public abstract class BuildAbstractTemplate {

    @Autowired(required = false)
    protected BuildService buildService;

    protected Map<String, String> pathMap = new HashMap<>(64);

    protected MultiValueMap<String, String> ftlMap = new LinkedMultiValueMap<>(64);

    protected String buildId = "";

    protected BaseProjectConfig rpasConfig;

    protected abstract String getTemplateType();

    protected abstract void initPath();

    protected abstract void resolve();

    protected abstract FileVO create();

    public final FileVO createProject(BaseProjectConfig baseProjectConfig) {
        rpasConfig = baseProjectConfig;
        initPath();
        resolve();
        FileVO fileVO = create();
        return fileVO;
    }
}
