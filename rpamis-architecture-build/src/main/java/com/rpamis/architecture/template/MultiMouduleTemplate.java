package com.rpamis.architecture.template;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.rpamis.architecture.config.InitConfig;
import com.rpamis.architecture.consts.ProjectTemplate;
import com.rpamis.architecture.consts.TemplateTypeEnum;
import com.rpamis.architecture.pojo.FileVO;
import com.rpamis.architecture.utils.StringUtils;
import com.rpamis.architecture.consts.ProjectKey;
import com.rpamis.architecture.consts.ProjectPath;
import com.rpamis.common.utils.SnowflakeUtil;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

/**
 * 多模块项目
 *
 * @author benym
 * @date 2022/7/21 10:37 上午
 */
@Component
public class MultiMouduleTemplate extends AbstractBuildTemplate {

    @Override
    protected String getTemplateType() {
        return TemplateTypeEnum.MULTI_MOUDULE.getCode();
    }

    @Override
    protected void initPath() {
        buildId = String.valueOf(SnowflakeUtil.get().next());
        String artifactId = rpasConfig.getProject().getArtifactId();
        String apiMoudule = artifactId + "-api";
        String daoMoudule = artifactId + "-dao";
        String serviceMoudule = artifactId + "-service";
        String webMoudule = artifactId + "-web";
        // 构建生成项目的基础存储路径
        String cachePath =
                ProjectPath.CACHETEMP_PATH + buildId + File.separator + artifactId + File.separator;
        // 获取基础包的路径
        String packageName = rpasConfig.getProject().getPackageName();
        String packagePath = packageName.replaceAll("\\.", "\\" + File.separator);
        // 构建出各模块的基础base路径
        String apiBasePath =
                cachePath + apiMoudule + File.separator;
        String daoBasePath =
                cachePath + daoMoudule + File.separator;
        String serviceBasePath =
                cachePath + serviceMoudule + File.separator;
        String webBasePath =
                cachePath + webMoudule + File.separator;
        // 构建出各模块的基础src路径
        String apiPackagePath =
                apiBasePath + ProjectPath.JAVA_PATH
                        + packagePath + File.separator;
        String daoResourcePath =
                daoBasePath + ProjectPath.RESOURCE_PATH;
        String daoPackagePath =
                daoBasePath + ProjectPath.JAVA_PATH
                        + packagePath + File.separator;
        String servicePackagePath =
                serviceBasePath + ProjectPath.JAVA_PATH
                        + packagePath + File.separator;
        String webApplicationPath =
                webBasePath + ProjectPath.JAVA_PATH;
        String webResourcePath =
                webBasePath + ProjectPath.RESOURCE_PATH;
        String webPackagePath =
                webBasePath + ProjectPath.JAVA_PATH
                        + packagePath + File.separator;
        // 构建出各个模块中基础文件夹的路径
        // api
        String apiMouduleApiDir = apiPackagePath + ProjectPath.API_PATH;
        String apiMoudulePojoDir = apiPackagePath + ProjectPath.POJO_PATH;
        String apiMouduleDtoDir = apiMoudulePojoDir + ProjectPath.DTO_PATH;
        String apiMouduleVoDir = apiMoudulePojoDir + ProjectPath.VO_PATH;
        String apiMouduleQueryDir = apiMoudulePojoDir + ProjectPath.QUERY_PATH;
        // dao
        String daoMouduleDaoDir = daoPackagePath + ProjectPath.DAO_PATH;
        String daoMouduleDaoImplDir = daoMouduleDaoDir + ProjectPath.DAO_IMPL_PATH;
        String daoMouduleMapperDir = daoPackagePath + ProjectPath.MAPPER_PATH;
        String daoMouduleDoDir = daoPackagePath + ProjectPath.DO_PATH;
        String daoMouduleMapperXmlDir = daoResourcePath + ProjectPath.MAPPER_XML_PATH;
        // service
        String serviceMouduleServiceDir = servicePackagePath + ProjectPath.SERVICE_PATH;
        String serviceMouduleServiceImplDir =
                serviceMouduleServiceDir + ProjectPath.SERVICE_IMPL_PATH;
        String serviceMoudulePojoDir = servicePackagePath + ProjectPath.POJO_PATH;
        String serviceMouduleDtoDir = serviceMoudulePojoDir + ProjectPath.DTO_PATH;
        String serviceMouduleVoDir = serviceMoudulePojoDir + ProjectPath.VO_PATH;
        String serviceMouduleQueryDir = serviceMoudulePojoDir + ProjectPath.QUERY_PATH;
        // web
        String webMouduleControllerDir = webPackagePath + ProjectPath.CONTROLLER_PATH;
        String webMouduleApplicationDir = webApplicationPath;
        String webMouduleResourceDir = webResourcePath;
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
        pathMap.put(ProjectKey.API_MOUDULE_API_DIR, apiMouduleApiDir);
        pathMap.put(ProjectKey.API_MOUDULE_POJO_DIR, apiMoudulePojoDir);
        pathMap.put(ProjectKey.API_MOUDULE_DTO_DIR, apiMouduleDtoDir);
        pathMap.put(ProjectKey.API_MOUDULE_VO_DIR, apiMouduleVoDir);
        pathMap.put(ProjectKey.API_MOUDULE_QUERY_DIR, apiMouduleQueryDir);
        pathMap.put(ProjectKey.DAO_MOUDULE_DAO_DIR, daoMouduleDaoDir);
        pathMap.put(ProjectKey.DAO_MOUDULE_DAO_IMPL_DIR, daoMouduleDaoImplDir);
        pathMap.put(ProjectKey.DAO_MOUDULE_MAPPER_DIR, daoMouduleMapperDir);
        pathMap.put(ProjectKey.DAO_MOUDULE_DO_DIR, daoMouduleDoDir);
        pathMap.put(ProjectKey.DAO_MOUDULE_MAPPER_XML_DIR, daoMouduleMapperXmlDir);
        pathMap.put(ProjectKey.SERVICE_MOUDULE_SERVICE_DIR, serviceMouduleServiceDir);
        pathMap.put(ProjectKey.SERVICE_MOUDULE_SERVICE_IMPL_DIR, serviceMouduleServiceImplDir);
        pathMap.put(ProjectKey.SERVICE_MOUDULE_POJO_DIR, serviceMoudulePojoDir);
        pathMap.put(ProjectKey.SERVICE_MOUDULE_DTO_DIR, serviceMouduleDtoDir);
        pathMap.put(ProjectKey.SERVICE_MOUDULE_VO_DIR, serviceMouduleVoDir);
        pathMap.put(ProjectKey.SERVICE_MOUDULE_QUERY_DIR, serviceMouduleQueryDir);
        pathMap.put(ProjectKey.WEB_MOUDULE_CONTROLLER_DIR, webMouduleControllerDir);
        pathMap.put(ProjectKey.WEB_MOUDULE_APPLICAITION_DIR, webMouduleApplicationDir);
        pathMap.put(ProjectKey.WEB_MOUDULE_RESOURCE_DIR, webMouduleResourceDir);
        pathMap.put(ProjectKey.YAML_RESOURCE_PATH, webMouduleResourceDir);
    }

    @Override
    protected void resolve() {
        if (rpasConfig.getDependency() != null) {
            if (rpasConfig.getDependency().getConsul() != null) {
                ftlMap.add(ProjectKey.YAML_RESOURCE_PATH, ProjectTemplate.BOOTSTRAP_YAML_NAME);
            }
            if (rpasConfig.getDependency().getDatabase() != null) {
                if (rpasConfig.getDependency().getDatabase().getCrud()) {
                    // todo crud模板
                    System.out.println(1);
                }
            }
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
                        String ftlFilePath = File.separator + InitConfig.getMap().get(ftlFileName) + File.separator + ftlFileName;
                        String mainName = "";
                        if (InitConfig.getMap().get(ftlFileName).equals("application")) {
                            mainName = StringUtils.getMainName(rpasConfig.getProject().getArtifactId());
                            rpasConfig.getProject().setMainName(mainName);
                        }
                        // 新建目标文件File，父路径(绝对路径)为Parent，ftl文件去除后缀为child
                        File file = new File(
                                pathMap.get(split[0]), mainName + StrUtil
                                .removeSuffix(split[1], ".ftl"));
                        buildService.generate(file, ftlFilePath, rpasConfig);
                    }
                });
            });
        }
        // 打包项目，并删除临时文件目录
        String artifactId = rpasConfig.getProject().getArtifactId();
        String saveZipPath = buildService.zipProject(artifactId, buildId);
        return new FileVO(buildId, saveZipPath);
    }
}
