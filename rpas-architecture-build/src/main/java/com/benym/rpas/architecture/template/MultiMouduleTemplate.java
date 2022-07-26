package com.benym.rpas.architecture.template;

import com.benym.rpas.architecture.config.BaseProjectConfig;
import com.benym.rpas.architecture.consts.ProjectKey;
import com.benym.rpas.architecture.consts.ProjectPath;
import com.benym.rpas.architecture.consts.TemplateType;
import com.benym.rpas.architecture.pojo.FileVO;
import com.benym.rpas.architecture.pojo.Project;
import java.io.File;
import java.util.UUID;
import org.springframework.stereotype.Component;

/**
 * 多模块项目
 *
 * @date 2022/7/21 10:37 上午
 */
@Component
public class MultiMouduleTemplate extends BuildAbstractTemplate {



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
        String cachePath = ProjectPath.CACHETEMP_PATH + File.separator + artifactId + File.separator;
        // 获取基础包的路径
        String packageName = rpasConfig.getProject().getPackageName();
        String packagePath = packageName.replaceAll("\\.","\\" + File.separator);
        // 构建出各模块的基础路径
        String apiPackagePath = cachePath + apiMoudule + File.separator + ProjectPath.JAVA_PATH + File.separator + packagePath + File.separator;
        String daoResourcePath = cachePath + daoMoudule + File.separator + ProjectPath.JAVA_PATH + File.separator + ProjectPath.RESOURCE_PATH + File.separator;
        String daoPackagePath = cachePath + daoMoudule + File.separator + ProjectPath.JAVA_PATH + File.separator + packagePath + File.separator;
        String servicePackagePath = cachePath + serviceMoudule + File.separator + ProjectPath.JAVA_PATH + File.separator + packagePath + File.separator;
        String webApplicationPath = cachePath + webMoudule + File.separator + ProjectPath.JAVA_PATH + File.separator;
        String webResourcePath = cachePath + webMoudule + File.separator + ProjectPath.JAVA_PATH + File.separator + ProjectPath.RESOURCE_PATH + File.separator;
        String webPackagePath = cachePath + webMoudule + File.separator + ProjectPath.JAVA_PATH + File.separator + packagePath + File.separator;
        // 构建出各个模块中基础文件夹的路径
        // api
        String apiMouduleApiDir = apiPackagePath + ProjectPath.API_PATH;
        String apiMoudulePojoDir = apiPackagePath + ProjectPath.POJO_PATH;
        String apiMouduleDtoDir = apiPackagePath + ProjectPath.DTO_PATH;
        String apiMouduleVoDir = apiPackagePath + ProjectPath.VO_PATH;
        String apiMouduleQueryDir = apiPackagePath + ProjectPath.QUERY_PATH;
        // dao
        String daoMouduleDaoDir = daoPackagePath + ProjectPath.DAO_PATH;
        String daoMouduleDaoImplDir = daoPackagePath + ProjectPath.DAO_IMPL_PATH;
        String daoMouduleMapperDir = daoPackagePath + ProjectPath.MAPPER_PATH;
        String daoMouduleDoDir = daoPackagePath + ProjectPath.DO_PATH;
        String daoMouduleMapperXmlDir = daoResourcePath + ProjectPath.MAPPER_XML_PATH;
        // service
        String serviceMouduleServiceDir = servicePackagePath + ProjectPath.SERVICE_PATH;
        String serviceMouduleServiceImplDir = servicePackagePath + ProjectPath.SERVICE_IMPL_PATH;
        String serviceMoudulePojoDir = servicePackagePath + ProjectPath.POJO_PATH;
        String serviceMouduleDtoDir = servicePackagePath + ProjectPath.DTO_PATH;
        String serviceMouduleVoDir = servicePackagePath + ProjectPath.VO_PATH;
        String serviceMouduleQueryDir = servicePackagePath + ProjectPath.QUERY_PATH;
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

    }

    @Override
    protected FileVO create() {
        return null;
    }
}
