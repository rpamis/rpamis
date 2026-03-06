package com.rpamis.architecture.template;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.rpamis.architecture.config.InitConfig;
import com.rpamis.architecture.consts.ProjectTemplate;
import com.rpamis.architecture.consts.TemplateTypeEnum;
import com.rpamis.architecture.pojo.*;
import com.rpamis.common.utils.StringUtils;
import com.rpamis.architecture.consts.ProjectKey;
import com.rpamis.architecture.consts.ProjectPath;
import com.rpamis.common.utils.SnowflakeUtil;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.Optional;

/**
 * 多模块项目
 *
 * @author benym
 * @date 2022/7/21 10:37 上午
 */
@Component
public class MultiModuleTemplate extends AbstractBuildTemplate {

	@Override
	protected String getTemplateType() {
		return TemplateTypeEnum.MULTI_MODULE.getCode();
	}

	@Override
	protected void initParentDirMap() {
		if (parentDirMap.isEmpty()) {
			// 初始化ftlName->parentDir的map
			buildService.initParentMap(parentDirMap, projectConfig.getSpringBootVersion());
		}
	}

	@Override
	protected void initPath() {
		buildId = String.valueOf(SnowflakeUtil.get().next());
		String artifactId = projectConfig.getProject().getArtifactId();
		String apiModule = artifactId + "-api";
		String daoModule = artifactId + "-dao";
		String serviceModule = artifactId + "-service";
		String webModule = artifactId + "-web";
		// 构建生成项目的基础存储路径
		String cachePath = ProjectPath.CACHETEMP_PATH + buildId + File.separator + artifactId + File.separator;
		// 获取基础包的路径
		String packageName = projectConfig.getProject().getPackageName();
		String packagePath = packageName.replaceAll("\\.", "\\" + File.separator);
		// 构建出各模块的基础base路径
		String apiBasePath = cachePath + apiModule + File.separator;
		String daoBasePath = cachePath + daoModule + File.separator;
		String serviceBasePath = cachePath + serviceModule + File.separator;
		String webBasePath = cachePath + webModule + File.separator;
		// 构建出各模块的基础src路径
		String apiPackagePath = apiBasePath + ProjectPath.JAVA_PATH + packagePath + File.separator;
		String daoResourcePath = daoBasePath + ProjectPath.RESOURCE_PATH;
		String daoPackagePath = daoBasePath + ProjectPath.JAVA_PATH + packagePath + File.separator;
		String servicePackagePath = serviceBasePath + ProjectPath.JAVA_PATH + packagePath + File.separator;
		String webApplicationPath = webBasePath + ProjectPath.JAVA_PATH;
		String webResourcePath = webBasePath + ProjectPath.RESOURCE_PATH;
		String webPackagePath = webBasePath + ProjectPath.JAVA_PATH + packagePath + File.separator;
		// 构建出各个模块中基础文件夹的路径
		// api
		String apiModuleApiDir = apiPackagePath + ProjectPath.API_PATH;
		String apiModulePojoDir = apiPackagePath + ProjectPath.POJO_PATH;
		String apiModuleDtoDir = apiModulePojoDir + ProjectPath.DTO_PATH;
		String apiModuleVoDir = apiModulePojoDir + ProjectPath.VO_PATH;
		String apiModuleQueryDir = apiModulePojoDir + ProjectPath.QUERY_PATH;
		// dao
		String daoModuleDaoDir = daoPackagePath + ProjectPath.DAO_PATH;
		String daoModuleDaoImplDir = daoModuleDaoDir + ProjectPath.DAO_IMPL_PATH;
		String daoModuleMapperDir = daoPackagePath + ProjectPath.MAPPER_PATH;
		String daoModuleDoDir = daoPackagePath + ProjectPath.DO_PATH;
		String daoModuleMapperXmlDir = daoResourcePath + ProjectPath.MAPPER_XML_PATH;
		// service
		String serviceModuleServiceDir = servicePackagePath + ProjectPath.SERVICE_PATH;
		String serviceModuleServiceImplDir = serviceModuleServiceDir + ProjectPath.SERVICE_IMPL_PATH;
		String serviceModulePojoDir = servicePackagePath + ProjectPath.POJO_PATH;
		String serviceModuleDtoDir = serviceModulePojoDir + ProjectPath.DTO_PATH;
		String serviceModuleVoDir = serviceModulePojoDir + ProjectPath.VO_PATH;
		String serviceModuleQueryDir = serviceModulePojoDir + ProjectPath.QUERY_PATH;
		// web
		String webModuleControllerDir = webPackagePath + ProjectPath.CONTROLLER_PATH;
		String webModuleApplicationDir = webApplicationPath;
		String webModuleResourceDir = webResourcePath;
		String webCommonDir = webPackagePath + ProjectPath.COMMON_PATH;
		String webConfigDir = webCommonDir + ProjectPath.CONFIG_PATH;
		String webConstantDir = webCommonDir + ProjectPath.CONSTANT_PATH;
		String webEnumsDir = webCommonDir + ProjectPath.ENUMS_PATH;
		String webFilterDir = webCommonDir + ProjectPath.FILTER_PATH;
		String webUtilsDir = webCommonDir + ProjectPath.UTILS_PATH;
		String webDaoDir = webPackagePath + ProjectPath.DAO_PATH;
		String webManagerDir = webPackagePath + ProjectPath.MANAGER_PATH;
		String webManagerImplDir = webManagerDir + ProjectPath.IMPL_PATH;
		String webServiceDir = webPackagePath + ProjectPath.SERVICE_PATH;
		String webServiceImplDir = webServiceDir + ProjectPath.IMPL_PATH;
		String webMapperDir = webModuleResourceDir + ProjectPath.MAPPER_XML_PATH;
		String webDubboFilterDir = webModuleResourceDir + ProjectPath.DUBBO_FILTER_PATH;
		// 将所有目录加入map中
		pathMap.put(ProjectKey.ROOT_BASE_PATH, cachePath);
		pathMap.put(ProjectKey.API_BASE_PATH, apiBasePath);
		pathMap.put(ProjectKey.DAO_BASE_PATH, daoBasePath);
		pathMap.put(ProjectKey.SERVICE_BASE_PATH, serviceBasePath);
		pathMap.put(ProjectKey.WEB_BASE_PATH, webBasePath);
		pathMap.put(ProjectKey.API_PACKAGE_PATH, apiPackagePath);
		pathMap.put(ProjectKey.DAO_RESOURCE_PATH, daoResourcePath);
		pathMap.put(ProjectKey.DAO_PACKAGE_PATH, daoPackagePath);
		pathMap.put(ProjectKey.SERVICE_PACKAGE_PATH, servicePackagePath);
		pathMap.put(ProjectKey.WEB_APPLICATION_PATH, webApplicationPath);
		pathMap.put(ProjectKey.WEB_RESOURCE_PATH, webResourcePath);
		pathMap.put(ProjectKey.WEB_PACKAGE_PATH, webPackagePath);
		pathMap.put(ProjectKey.API_MODULE_API_DIR, apiModuleApiDir);
		pathMap.put(ProjectKey.API_MODULE_POJO_DIR, apiModulePojoDir);
		pathMap.put(ProjectKey.API_MODULE_DTO_DIR, apiModuleDtoDir);
		pathMap.put(ProjectKey.API_MODULE_VO_DIR, apiModuleVoDir);
		pathMap.put(ProjectKey.API_MODULE_QUERY_DIR, apiModuleQueryDir);
		pathMap.put(ProjectKey.DAO_MODULE_DAO_DIR, daoModuleDaoDir);
		pathMap.put(ProjectKey.DAO_MODULE_DAO_IMPL_DIR, daoModuleDaoImplDir);
		pathMap.put(ProjectKey.DAO_MODULE_MAPPER_DIR, daoModuleMapperDir);
		pathMap.put(ProjectKey.DAO_MODULE_DO_DIR, daoModuleDoDir);
		pathMap.put(ProjectKey.DAO_MODULE_MAPPER_XML_DIR, daoModuleMapperXmlDir);
		pathMap.put(ProjectKey.SERVICE_MODULE_SERVICE_DIR, serviceModuleServiceDir);
		pathMap.put(ProjectKey.SERVICE_MODULE_SERVICE_IMPL_DIR, serviceModuleServiceImplDir);
		pathMap.put(ProjectKey.SERVICE_MODULE_POJO_DIR, serviceModulePojoDir);
		pathMap.put(ProjectKey.SERVICE_MODULE_DTO_DIR, serviceModuleDtoDir);
		pathMap.put(ProjectKey.SERVICE_MODULE_VO_DIR, serviceModuleVoDir);
		pathMap.put(ProjectKey.SERVICE_MODULE_QUERY_DIR, serviceModuleQueryDir);
		pathMap.put(ProjectKey.WEB_MODULE_CONTROLLER_DIR, webModuleControllerDir);
		pathMap.put(ProjectKey.WEB_MODULE_APPLICATION_DIR, webModuleApplicationDir);
		pathMap.put(ProjectKey.WEB_MODULE_RESOURCE_DIR, webModuleResourceDir);
		pathMap.put(ProjectKey.YAML_RESOURCE_PATH, webModuleResourceDir);
		pathMap.put(ProjectKey.WEB_COMMON_DIR, webCommonDir);
		pathMap.put(ProjectKey.WEB_CONFIG_DIR, webConfigDir);
		pathMap.put(ProjectKey.WEB_CONSTANT_DIR, webConstantDir);
		pathMap.put(ProjectKey.WEB_ENUMS_DIR, webEnumsDir);
		pathMap.put(ProjectKey.WEB_FILETER_DIR, webFilterDir);
		pathMap.put(ProjectKey.WEB_UTILS_DIR, webUtilsDir);
		pathMap.put(ProjectKey.WEB_DAO_DIR, webDaoDir);
		pathMap.put(ProjectKey.WEB_MANAGER_DIR, webManagerDir);
		pathMap.put(ProjectKey.WEB_MANAGERIMPL_DIR, webManagerImplDir);
		pathMap.put(ProjectKey.WEB_SERVICE_DIR, webServiceDir);
		pathMap.put(ProjectKey.WEB_SERVICEIMPL_DIR, webServiceImplDir);
		pathMap.put(ProjectKey.WEB_MAPPER_DIR, webMapperDir);
		pathMap.put(ProjectKey.WEB_MOUDULE_CONTROLLER_DIR, webModuleControllerDir);
		pathMap.put(ProjectKey.WEB_MOUDULE_APPLICAITION_DIR, webModuleApplicationDir);
		pathMap.put(ProjectKey.WEB_MOUDULE_RESOURCE_DIR, webModuleResourceDir);
		pathMap.put(ProjectKey.WEB_DUBBOFILTER_DIR, webDubboFilterDir);
		pathMap.put(ProjectKey.DOCKER_PATH, webBasePath);
		pathMap.put(ProjectKey.IGNORE_PATH, cachePath);
	}

	@Override
	protected void resolve() {
		if (projectConfig.getDependency() != null) {
			if (projectConfig.getDependency().getConsul() != null) {
				ftlMap.add(ProjectKey.YAML_RESOURCE_PATH, ProjectTemplate.BOOTSTRAP_YAML_NAME);
			}
			if (projectConfig.getDependency().getDatabase() != null) {
				if (Boolean.TRUE.equals(projectConfig.getDependency().getDatabase().getCrud())) {
					// todo crud模板
					System.out.println(1);
				}
			}
		}
		SpringBootVersion springBootVersion = projectConfig.getSpringBootVersion();
		boolean isSpringBoot3 = springBootVersion.getCode().equals(SpringBootVersion.V2.getCode());
		Optional<Dependency> dependency = Optional.ofNullable(projectConfig.getDependency());
		Boolean springCloudEnabled = dependency.map(Dependency::getSpringcloud)
			.map(SpringCloud::getEnabled)
			.orElse(false);
		Boolean databaseEnabled = dependency.map(Dependency::getDatabase).map(Database::getEnabled).orElse(false);
		Boolean kafkaEnabled = dependency.map(Dependency::getKafka).map(Kafka::getEnabled).orElse(false);
		Boolean redisEnabled = dependency.map(Dependency::getRedis).map(Redis::getEnabled).orElse(false);
		Boolean mongoEnabled = dependency.map(Dependency::getMongoDb).map(MongoDb::getEnabled).orElse(false);
		if (springCloudEnabled) {
			ftlMap.add(ProjectKey.PROPERTIES_RESOURCE_PATH, ProjectTemplate.BOOTSTRAP_YML);
		}
		if (databaseEnabled) {
			ftlMap.add(ProjectKey.WEB_CONFIG_DIR, ProjectTemplate.MYBATISPLUS_CONFIG);
		}
		if (redisEnabled) {
			ftlMap.add(ProjectKey.WEB_CONFIG_DIR, ProjectTemplate.REDISSION_CONFIG);
		}
		if (mongoEnabled) {
			ftlMap.add(ProjectKey.WEB_CONFIG_DIR, ProjectTemplate.MONGO_CONFIG);
		}
		ftlMap.add(ProjectKey.YAML_RESOURCE_PATH, ProjectTemplate.APPLICATION_YAML_NAME);
		ftlMap.add(ProjectKey.WEB_PACKAGE_PATH, ProjectTemplate.APPLICATION_JAVA_NAME);
		ftlMap.add(ProjectKey.ROOT_BASE_PATH, ProjectTemplate.ROOT_POM_NAME);
		ftlMap.add(ProjectKey.API_BASE_PATH, ProjectTemplate.API_POM_NAME);
		ftlMap.add(ProjectKey.DAO_BASE_PATH, ProjectTemplate.DAO_POM_NAME);
		ftlMap.add(ProjectKey.SERVICE_BASE_PATH, ProjectTemplate.SERVICE_POM_NAME);
		ftlMap.add(ProjectKey.WEB_BASE_PATH, ProjectTemplate.WEB_POM_NAME);
	}

	@Override
	protected FileVO create() {
		// 创建初始文件夹
		pathMap.forEach((key, value) -> FileUtil.mkdir(value));
		// 根据模板生成文件
		if (!ftlMap.isEmpty()) {
			ftlMap.forEach((key, value) -> {
				List<String> valueList = ftlMap.get(key);
				valueList.forEach(ftlFileName -> {
					if (ftlFileName.contains("#")) {
						// 获取模板文件全称
						String[] split = ftlFileName.split("#");
						// 获取模板文件相对templates文件夹的位置，包括父路径，比如/application/xxx.ftl
						String ftlFilePath = File.separator + InitConfig.getMap().get(ftlFileName) + File.separator
								+ ftlFileName;
						String mainName = "";
						SpringBootVersion springBootVersion = projectConfig.getSpringBootVersion();
						boolean isSpringBoot3 = springBootVersion.getCode().equals(SpringBootVersion.V2.getCode());
						String applicationPath;
						if (isSpringBoot3) {
							applicationPath = "springboot3" + File.separator + "application";
						}
						else {
							applicationPath = "springboot2" + File.separator + "application";
						}
						if (applicationPath.equals(parentDirMap.get(ftlFileName))) {
							mainName = StringUtils.getMainName(projectConfig.getProject().getArtifactId());
							projectConfig.getProject().setMainName(mainName);
						}
						// 新建目标文件File，父路径(绝对路径)为Parent，ftl文件去除后缀为child
						File file = new File(pathMap.get(split[0]), mainName + StrUtil.removeSuffix(split[1], ".ftl"));
						buildService.generate(file, ftlFilePath, projectConfig);
					}
				});
			});
		}
		// 打包项目，并删除临时文件目录
		String artifactId = projectConfig.getProject().getArtifactId();
		String saveZipPath = buildService.zipProject(artifactId, buildId);
		return new FileVO(buildId, saveZipPath);
	}

}
