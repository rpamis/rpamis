package com.benym.rpas.architecture.template;

import com.benym.rpas.architecture.consts.TemplateType;
import java.util.HashMap;
import java.util.Map;

/**
 * 策略模式+工厂模式
 * 模板工厂
 *
 * @date 2022/7/21 10:53 上午
 */
public class TemplateFactory {

    private static Map<String, BuildAbstractTemplate> templateMap = new HashMap<>();

    public static BuildAbstractTemplate getTemplate(TemplateType templateType) {
        return templateMap.get(templateType.getCode());
    }

    public static void register(TemplateType templateType,
            BuildAbstractTemplate buildAbstractTemplate) {
        templateMap.put(templateType.getCode(), buildAbstractTemplate);
    }
}
