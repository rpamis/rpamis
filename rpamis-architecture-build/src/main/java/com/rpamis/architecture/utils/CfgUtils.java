package com.rpamis.architecture.utils;

import com.rpamis.architecture.consts.ProjectPath;
import com.rpamis.exception.dto.BizNoStackException;
import freemarker.template.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

/**
 * freemarker Configuration工具类
 *
 * @author benym
 * @date 2022/9/2 10:25
 */
@Component
public class CfgUtils {

	private static volatile Configuration cfg;

	@Value("${rpamis.activate}")
	private String activate;

	public Configuration getCfg() {
		if (cfg == null) {
			synchronized (CfgUtils.class) {
				if (cfg == null) {
					try {
						cfg = new Configuration(Configuration.VERSION_2_3_30);
						File file;
						if ("local".equals(activate)) {
							file = new File(ProjectPath.COPYTEMPLATES_PATH);
						}
						else if ("linux".equals(activate)) {
							file = new File("/data/app/templates/");
						}
						else {
							throw new BizNoStackException("请在application.properties设置激活环境，linux or local");
						}
						cfg.setDirectoryForTemplateLoading(file);
						cfg.setDefaultEncoding("UTF-8");
					}
					catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return cfg;
	}

	private CfgUtils() {
		throw new IllegalStateException("工具类，禁止实例化");
	}

}
