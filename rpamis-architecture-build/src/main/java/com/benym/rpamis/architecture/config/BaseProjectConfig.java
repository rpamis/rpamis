package com.benym.rpamis.architecture.config;

import com.benym.rpamis.architecture.consts.TemplateType;
import com.benym.rpamis.architecture.pojo.Dependency;
import com.benym.rpamis.architecture.pojo.Project;
import java.io.Serializable;
import javax.validation.Valid;

/**
 * @date 2022/7/20 5:01 下午
 * @author benym
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
