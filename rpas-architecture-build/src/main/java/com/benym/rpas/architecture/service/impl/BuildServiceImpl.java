package com.benym.rpas.architecture.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import cn.hutool.json.JSONUtil;
import com.benym.rpas.architecture.config.BaseProjectConfig;
import com.benym.rpas.architecture.consts.ProjectPath;
import com.benym.rpas.architecture.pojo.FileVO;
import com.benym.rpas.architecture.service.BuildService;
import com.benym.rpas.architecture.template.BuildAbstractTemplate;
import com.benym.rpas.architecture.template.TemplateFactory;
import com.benym.rpas.architecture.utils.CfgUtils;
import com.benym.rpas.common.dto.exception.ExceptionFactory;
import freemarker.template.TemplateException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @date 2022/7/20 4:48 下午
 */
@Service
public class BuildServiceImpl implements BuildService {


    private static final Logger logger = LoggerFactory.getLogger(BuildServiceImpl.class);

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
        try {
            if (!file.exists()) {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                file.createNewFile();
            }
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file));
            CfgUtils.getCfg().getTemplate(templatesFtl, "UTF-8")
                    .process(baseProjectConfig, outputStreamWriter);
            outputStreamWriter.flush();
            outputStreamWriter.close();
        } catch (IOException | TemplateException e) {
            logger.error("文件生成异常{}", JSONUtil.toJsonStr(e.getMessage()));
            throw ExceptionFactory.bizException("文件生成异常");
        }
    }

    @Override
    public String zipProject(String artifactId, String buildId) {
        String genProjectPath =
                ProjectPath.CACHETEMP_PATH + buildId + File.separator + artifactId + File.separator;
        String saveZipPath = ProjectPath.CACHETEMP_PATH + buildId + File.separator + artifactId + ".zip";
        ZipUtil.zip(genProjectPath, saveZipPath);
        FileUtil.del(genProjectPath);
        return saveZipPath;
    }

    @Override
    public void copyFtlToCacheDir(Map<String, String> parentDirMap) {
        try {
            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resolver
                    .getResources("classpath:templates/**/*.ftl");
            for (Resource resource : resources) {
                String filename = resource.getFilename();
                String path = URLDecoder.decode(resource.getURL().getPath(), "UTF-8");
                List<String> split = StrUtil.split(path, "/");
                if (!split.isEmpty()) {
                    parentDirMap.put(split.get(split.size() - 1), split.get(split.size() - 2));
                }
                copy(filename, parentDirMap);
            }
            logger.info("copy templates done!");
        } catch (IOException e) {
            logger.error("拷贝模版文件异常:{}", e.getMessage());
            throw ExceptionFactory.bizException("拷贝模板文件异常");
        }
    }

    private static void copy(String ftlName, Map<String, String> parentDirMap) {
        try {
            String ftlPath = ProjectPath.COPYTEMPLATES_PATH + parentDirMap.get(ftlName)
                    + File.separator + ftlName;
            String sourceTemplatesPath = "templates" + File.separator;
            //检查项目运行时的src下的对应路径
            File newFile = new File(ftlPath);
            //读取ftl复制一份到cache路径下
            ClassPathResource classPathResource = new ClassPathResource
                    (sourceTemplatesPath + parentDirMap.get(ftlName) + File.separator + ftlName);
            InputStream ftlStream = classPathResource.getInputStream();
            byte[] certData = IOUtils.toByteArray(ftlStream);
            FileUtils.writeByteArrayToFile(newFile, certData);
        } catch (IOException e) {
            logger.error("复制ftl文件失败{}", JSONUtil.toJsonStr(e.getMessage()));
            throw ExceptionFactory.bizException("复制ftl文件失败");
        }
    }

    @Override
    public void download(String id) {
        String fileName;
        HttpServletResponse response = ((ServletRequestAttributes) Objects
                .requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();
        synchronized (this) {
            try {
                List<String> list = FileUtil.listFileNames(ProjectPath.CACHETEMP_PATH + id);
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
                String filePath = ProjectPath.CACHETEMP_PATH + id + File.separator + fileName;
                outputStream.write(FileUtil.readBytes(filePath));
                outputStream.flush();
            } catch (IOException e) {
                logger.error("打包文件异常{}", JSONUtil.toJsonStr(e.getStackTrace()));
                throw ExceptionFactory.bizException("打包文件异常");
            }
        }
    }
}
