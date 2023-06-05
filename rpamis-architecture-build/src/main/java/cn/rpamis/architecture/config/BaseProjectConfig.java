package cn.rpamis.architecture.config;

import cn.rpamis.architecture.consts.TemplateTypeEnum;
import cn.rpamis.architecture.pojo.Dependency;
import cn.rpamis.architecture.pojo.Project;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import javax.validation.Valid;

/**
 * 项目基本配置
 *
 * @date 2022/7/20 5:01 下午
 * @author benym
 */
@Data
@Builder
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
    private TemplateTypeEnum templateTypeEnum;
}
