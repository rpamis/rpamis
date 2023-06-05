package com.rpamis.architecture.template;

import com.rpamis.architecture.consts.TemplateTypeEnum;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 策略模式+工厂模式
 * 模板工厂
 *
 * @author benym
 * @date 2022/7/21 10:53 上午
 */
@Component
public class TemplateFactory implements InitializingBean {

    @Autowired
    private ApplicationContext applicationContext;

    private Map<String, AbstractBuildTemplate> templateMap;

    public AbstractBuildTemplate getTemplate(TemplateTypeEnum templateTypeEnum) {
        return templateMap.get(templateTypeEnum.getCode());
    }

    @Override
    public void afterPropertiesSet() {
        Map<String, AbstractBuildTemplate> beansOfType = applicationContext.getBeansOfType(AbstractBuildTemplate.class);
        templateMap = Optional.of(beansOfType)
                .map(beansOfTypeMap -> beansOfTypeMap.values().stream()
                        .filter(template -> !StringUtils.isEmpty(template.getTemplateType()))
                        .collect(Collectors.toMap(AbstractBuildTemplate::getTemplateType, Function.identity())))
                .orElse(new HashMap<>(8));
    }
}
