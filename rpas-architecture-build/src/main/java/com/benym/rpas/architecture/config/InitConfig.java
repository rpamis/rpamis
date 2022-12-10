package com.benym.rpas.architecture.config;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.benym.rpas.architecture.consts.ProjectPath;
import com.benym.rpas.common.dto.exception.ExceptionFactory;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @date 2022/12/10 23:18
 */
@Configuration
public class InitConfig implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(InitConfig.class);

    public static final Map<String, String> parentDirMap = new HashMap<>(32);

    private static void copyFtlToCacheDir() {
        try {
            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resolver
                    .getResources("classpath:templates/**/*.ftl");
            for (Resource resource : resources) {
                String filename = resource.getFilename();
                String path = URLDecoder.decode(resource.getURL().getPath(), "UTF-8");
                List<String> split = StrUtil.split(path, "/");
                if (!split.isEmpty()) {
                    InitConfig.parentDirMap.put(split.get(split.size() - 1), split.get(split.size() - 2));
                }
                copy(filename);
            }
            logger.info("copy templates done!");
        } catch (IOException e) {
            throw ExceptionFactory.bizException("拷贝模板文件异常", e);
        }
    }

    private static void copy(String ftlName) {
        try {
            String ftlPath = ProjectPath.COPYTEMPLATES_PATH + InitConfig.parentDirMap.get(ftlName)
                    + File.separator + ftlName;
            String sourceTemplatesPath = "templates" + File.separator;
            //检查项目运行时的src下的对应路径
            File newFile = new File(ftlPath);
            //读取ftl复制一份到cache路径下
            ClassPathResource classPathResource = new ClassPathResource
                    (sourceTemplatesPath + InitConfig.parentDirMap.get(ftlName) + File.separator + ftlName);
            InputStream ftlStream = classPathResource.getInputStream();
            byte[] certData = IOUtils.toByteArray(ftlStream);
            FileUtils.writeByteArrayToFile(newFile, certData);
        } catch (IOException e) {
            logger.error("复制ftl文件失败{}", JSONUtil.toJsonStr(e.getMessage()));
            throw ExceptionFactory.bizException("复制ftl文件失败", e);
        }
    }

    @Override
    public void run(String... args) throws Exception {
        copyFtlToCacheDir();
    }
}
