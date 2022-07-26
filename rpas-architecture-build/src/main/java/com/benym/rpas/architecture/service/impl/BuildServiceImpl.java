package com.benym.rpas.architecture.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONUtil;
import com.benym.rpas.architecture.config.BaseProjectConfig;
import com.benym.rpas.architecture.consts.ProjectPath;
import com.benym.rpas.architecture.consts.TemplateType;
import com.benym.rpas.architecture.pojo.FileVO;
import com.benym.rpas.architecture.service.BuildService;
import com.benym.rpas.architecture.template.BuildAbstractTemplate;
import com.benym.rpas.architecture.template.MultiMouduleTemplate;
import com.benym.rpas.architecture.template.TemplateFactory;
import com.benym.rpas.common.dto.exception.ExceptionFactory;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URLEncoder;
import java.util.List;
import java.util.Objects;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @date 2022/7/20 4:48 下午
 */
@Service
public class BuildServiceImpl implements BuildService {


    private static final Logger logger = LoggerFactory.getLogger(BuildServiceImpl.class);

    private static Configuration cfg;

    static {
        try {
            cfg = new Configuration(Configuration.VERSION_2_3_30);
            File file = new File(ProjectPath.BUILD_PATH + "/src/main/resources/templates");
            cfg.setDirectoryForTemplateLoading(file);
            cfg.setDefaultEncoding("UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public FileVO architectureBuild(BaseProjectConfig baseProjectConfig) {
        BuildAbstractTemplate template;
        try {
            template = TemplateFactory.getTemplate(baseProjectConfig.getTemplateType());
        } catch (Exception e) {
            logger.error("获取模板异常{}", JSONUtil.toJsonStr(e.getStackTrace()));
            throw ExceptionFactory.bizException("获取模板异常", e.getMessage());
        }
        return template.createProject(baseProjectConfig);
    }

    @Override
    public void generate(File file, String templatesFtl, BaseProjectConfig baseProjectConfig) {
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            OutputStreamWriter outputStreamWriter  = new OutputStreamWriter(new FileOutputStream(file));
            cfg.getTemplate(templatesFtl,"UTF-8").process(baseProjectConfig, outputStreamWriter);
            outputStreamWriter.flush();
            outputStreamWriter.close();
        } catch (IOException | TemplateException e) {
            logger.error("文件生成异常{}", JSONUtil.toJsonStr(e.getStackTrace()));
            throw ExceptionFactory.bizException("文件生成异常");
        }
    }

    @Override
    public void download(String id) {
        String fileName;
        HttpServletResponse response = ((ServletRequestAttributes) Objects
                .requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();
        synchronized (this) {
            try {
                List<String> list = FileUtil.listFileNames(id);
                if (list.isEmpty()) {
                    throw ExceptionFactory.bizException();
                }
                fileName = list.get(0);
            } catch (Exception e) {
                logger.error("下载文件不存在{}", JSONUtil.toJsonStr(e.getStackTrace()));
                throw ExceptionFactory.bizException("下载文件不存在");
            }
            try (ServletOutputStream outputStream = Objects.requireNonNull(response)
                    .getOutputStream()) {
                response.setContentType("application/x-download");
                response.addHeader("Content-Disposition",
                        "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
                outputStream.write(FileUtil.readBytes(fileName));
                outputStream.flush();
            } catch (IOException e) {
                logger.error("打包文件异常{}", JSONUtil.toJsonStr(e.getStackTrace()));
                throw ExceptionFactory.bizException("打包文件异常");
            }
        }
    }
}
