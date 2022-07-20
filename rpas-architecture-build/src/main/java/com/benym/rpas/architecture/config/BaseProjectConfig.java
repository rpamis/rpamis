package com.benym.rpas.architecture.config;

import com.benym.rpas.architecture.pojo.Project;
import java.io.Serializable;

/**
 * @date 2022/7/20 5:01 下午
 */
public class BaseProjectConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    private Project project;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
