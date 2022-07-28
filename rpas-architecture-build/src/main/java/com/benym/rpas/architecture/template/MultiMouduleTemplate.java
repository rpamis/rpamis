package com.benym.rpas.architecture.template;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.benym.rpas.architecture.config.BaseProjectConfig;
import com.benym.rpas.architecture.consts.ProjectKey;
import com.benym.rpas.architecture.consts.ProjectPath;
import com.benym.rpas.architecture.consts.ProjectTemplate;
import com.benym.rpas.architecture.consts.TemplateType;
import com.benym.rpas.architecture.pojo.FileVO;
import com.benym.rpas.architecture.pojo.Project;
import com.benym.rpas.architecture.service.BuildService;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 多模块项目
 *
 * @date 2022/7/21 10:37 上午
 */
@Component
public class MultiMouduleTemplate extends BuildAbstractTemplate {


    @Autowired
    private BuildService buildService;


    @Override
    public void run(String... args) throws Exception {
        TemplateFactory.register(TemplateType.MULTI_MOUDULE, this);
    }

    @Override
    protected void initPath() {
        String artifactId = rpasConfig.getProject().getArtifactId();
        String apiMoudule = artifactId + "-api";
        String daoMoudule = artifactId + "-dao";
        String serviceMoudule = artifactId + "-service";
        String webMoudule = artifactId + "-web";
        // 构建生成项目的基础存储路径
        String cachePath =
                ProjectPath.CACHETEMP_PATH + artifactId + File.separator;
        // 获取基础包的路径
        String packageName = rpasConfig.getProject().getPackageName();
        String packagePath = packageName.replaceAll("\\.", "\\" + File.separator);
        // 构建出各模块的基础路径
        String apiPackagePath =
                cachePath + apiMoudule + File.separator + ProjectPath.JAVA_PATH
                        + packagePath + File.separator;
        String daoResourcePath =
                cachePath + daoMoudule + File.separator + ProjectPath.RESOURCE_PATH;
        String daoPackagePath =
                cachePath + daoMoudule + File.separator + ProjectPath.JAVA_PATH
                        + packagePath + File.separator;
        String servicePackagePath =
                cachePath + serviceMoudule + File.separator + ProjectPath.JAVA_PATH
                        + packagePath + File.separator;
        String webApplicationPath =
                cachePath + webMoudule + File.separator + ProjectPath.JAVA_PATH;
        String webResourcePath =
                cachePath + webMoudule + File.separator + ProjectPath.RESOURCE_PATH;
        String webPackagePath =
                cachePath + webMoudule + File.separator + ProjectPath.JAVA_PATH
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
//        ftlMap.add(ProjectKey.YAML_RESOURCE_PATH, ProjectTemplate.APPLICATION_YAML_NAME);
        ftlMap.add(ProjectKey.WEB_APPLICATION_PATH, ProjectTemplate.APPLICATION_JAVA_NAME);
    }

    @Override
    protected FileVO create() {
        // 创建初始文件夹
        pathMap.forEach((key, value) -> FileUtil.mkdir(value));
        // 根据模板生成文件
        ftlMap.forEach((key, value) -> {
            List<String> valueList = ftlMap.get(key);
            valueList.forEach(ftlFileName -> {
                String[] split = ftlFileName.split("#");
                String s = pathMap.get(split[0]);
                String ftlFilePath = "/application/" + ftlFileName;
                File file = new File(
                        pathMap.get(split[0]), rpasConfig.getProject().getArtifactId() + StrUtil
                        .removeSuffix(split[1], ".ftl"));
                buildService.generate(file, ftlFilePath, rpasConfig);
            });
        });
        return null;
    }
}
