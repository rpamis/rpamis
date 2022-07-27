package com.benym.rpas.architecture.config;

import com.benym.rpas.architecture.consts.TemplateType;
import com.benym.rpas.architecture.pojo.Consul;
import com.benym.rpas.architecture.pojo.Dependency;
import com.benym.rpas.architecture.pojo.Feign;
import com.benym.rpas.architecture.pojo.Project;
import java.io.Serializable;
import javax.validation.Valid;

/**
 * @date 2022/7/20 5:01 下午
 */
public class BaseProjectConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @Valid
    private Project project;

    private Dependency dependency;

    private TemplateType templateType;

    public TemplateType getTemplateType() {
        return templateType;
    }

    public void setTemplateType(TemplateType templateType) {
        this.templateType = templateType;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Dependency getDependency() {
        return dependency;
    }

    public void setDependency(Dependency dependency) {
        this.dependency = dependency;
    }
}
