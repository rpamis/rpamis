package com.benym.rpas.architecture.utils;

import cn.hutool.core.lang.generator.SnowflakeGenerator;
import com.benym.rpas.architecture.consts.ProjectPath;
import freemarker.template.Configuration;

import java.io.File;
import java.io.IOException;

/**
 * @Time : 2022/9/2 10:25
 */
public class CfgUtils {

    private static volatile Configuration cfg;

    public static Configuration getCfg() {
        if (cfg == null) {
            synchronized (Configuration.class) {
                if (cfg == null) {
                    try {
                        cfg = new Configuration(Configuration.VERSION_2_3_30);
                        File file = new File(ProjectPath.COPYTEMPLATES_PATH);
                        cfg.setDirectoryForTemplateLoading(file);
                        cfg.setDefaultEncoding("UTF-8");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return cfg;
    }
}
