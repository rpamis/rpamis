package com.rpamis.architecture.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import com.rpamis.architecture.config.BaseProjectConfig;
import com.rpamis.architecture.consts.ProjectPath;
import com.rpamis.architecture.pojo.FileVO;
import com.rpamis.architecture.pojo.SpringBootVersion;
import com.rpamis.architecture.service.BuildService;
import com.rpamis.architecture.template.AbstractBuildTemplate;
import com.rpamis.architecture.template.TemplateFactory;
import com.rpamis.architecture.utils.CfgUtils;
import com.rpamis.common.utils.FileUtil;
import com.rpamis.exception.dto.BizException;
import com.rpamis.exception.dto.BizNoStackException;
import com.rpamis.exception.dto.ExceptionFactory;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 生成类Service实现
 *
 * @author benym
 * @date 2022/7/20 4:48 下午
 */
@Service
public class BuildServiceImpl implements BuildService {

	private static final Logger logger = LoggerFactory.getLogger(BuildServiceImpl.class);

	@Autowired
	private TemplateFactory templateFactory;

	@Autowired
	private CfgUtils cfgUtils;

	@Override
	public FileVO architectureBuild(BaseProjectConfig baseProjectConfig) {
		AbstractBuildTemplate template;
		try {
			template = templateFactory.getTemplate(baseProjectConfig.getTemplateType());
		}
		catch (Exception e) {
			throw ExceptionFactory.bizException("获取模板异常", e);
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
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file),
					StandardCharsets.UTF_8);
			cfgUtils.getCfg().getTemplate(templatesFtl, "UTF-8").process(baseProjectConfig, outputStreamWriter);
			outputStreamWriter.flush();
			outputStreamWriter.close();
		}
		catch (IOException | TemplateException e) {
			throw ExceptionFactory.bizException("文件生成异常", e);
		}
	}

	@Override
	public String zipProject(String artifactId, String buildId) {
		String genProjectPath = ProjectPath.CACHETEMP_PATH + buildId + File.separator + artifactId + File.separator;
		String saveZipPath = ProjectPath.CACHETEMP_PATH + buildId + File.separator + artifactId + ".zip";
		FileUtil.generateGitKeepFiles(genProjectPath);
		ZipUtil.zip(genProjectPath, saveZipPath);
		cn.hutool.core.io.FileUtil.del(genProjectPath);
		return saveZipPath;
	}

	@Override
	public void initParentMap(Map<String, String> parentMap, SpringBootVersion springBootVersion) {
		try {
			ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
			Resource[] resources;
			if (SpringBootVersion.V1.getCode().equals(springBootVersion.getCode())) {
				resources = resolver.getResources("classpath:templates/springboot2/*/*.ftl");
			}
			else if (SpringBootVersion.V2.getCode().equals(springBootVersion.getCode())) {
				resources = resolver.getResources("classpath:templates/springboot3/*/*.ftl");
			}
			else {
				throw new BizNoStackException("未找到生成资源路径");
			}
			for (Resource resource : resources) {
				String path = URLDecoder.decode(resource.getURL().getPath(), StandardCharsets.UTF_8);
				List<String> split = StrUtil.split(path, "/");
				if (!split.isEmpty()) {
					parentMap.put(split.get(split.size() - 1),
							split.get(split.size() - 3).toLowerCase() + File.separator + split.get(split.size() - 2));
				}
			}
		}
		catch (IOException e) {
			logger.error("初始化parentDirMap异常:{}", e.getMessage());
			throw new BizException("初始化parentDirMap异常", e);
		}
	}

	@Override
	public void download(String id) {
		String fileName;
		HttpServletResponse response = ((ServletRequestAttributes) Objects
			.requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();
		synchronized (this) {
			try {
				List<String> list = cn.hutool.core.io.FileUtil.listFileNames(ProjectPath.CACHETEMP_PATH + id);
				if (list.isEmpty()) {
					throw ExceptionFactory.bizNoStackException("CACHE list为空");
				}
				fileName = list.get(0);
			}
			catch (Exception e) {
				throw ExceptionFactory.bizException("下载文件不存在", e);
			}
			try (ServletOutputStream outputStream = Objects.requireNonNull(response).getOutputStream()) {
				response.setContentType("application/x-download");
				response.addHeader("Content-Disposition",
						"attachment;filename=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8));
				String filePath = ProjectPath.CACHETEMP_PATH + id + File.separator + fileName;
				outputStream.write(cn.hutool.core.io.FileUtil.readBytes(filePath));
				outputStream.flush();
			}
			catch (IOException e) {
				throw ExceptionFactory.bizException("打包文件异常", e);
			}
		}
	}

}
