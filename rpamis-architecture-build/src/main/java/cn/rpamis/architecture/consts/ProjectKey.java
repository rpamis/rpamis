package cn.rpamis.architecture.consts;

/**
 * 项目Key
 *
 * @author benym
 * @date 2022/7/25 5:00 下午
 */
public final class ProjectKey {

    public static final String ROOT_BASE_PATH = "rootBasePath";
    public static final String API_BASE_PATH = "apiBasePath";
    public static final String DAO_BASE_PATH = "daoBasePath";
    public static final String SERVICE_BASE_PATH = "serviceBasePath";
    public static final String WEB_BASE_PATH = "webBasePath";
    public static final String YAML_RESOURCE_PATH = "yamlResourcePath";
    public static final String API_PACKAGE_PATH = "apiPackagePath";
    public static final String DAO_RESOURCE_PATH = "daoResourcePath";
    public static final String DAO_PACKAGE_PATH = "daoPackagePath";
    public static final String SERVICE_PACKAGE_PATH = "servicePackagePath";
    public static final String WEB_APPLICATION_PATH = "webApplicationPath";
    public static final String WEB_RESOURCE_PATH = "webResourcePath";
    public static final String WEB_PACKAGE_PATH = "webPackagePath";
    public static final String API_MOUDULE_API_DIR = "apiMouduleApiDir";
    public static final String API_MOUDULE_POJO_DIR = "apiMoudulePojoDir";
    public static final String API_MOUDULE_DTO_DIR = "apiMouduleDtoDir";
    public static final String API_MOUDULE_VO_DIR = "apiMouduleVoDir";
    public static final String API_MOUDULE_QUERY_DIR = "apiMouduleQueryDir";
    public static final String DAO_MOUDULE_DAO_DIR = "daoMouduleDaoDir";
    public static final String DAO_MOUDULE_DAO_IMPL_DIR = "daoMouduleDaoImplDir";
    public static final String DAO_MOUDULE_MAPPER_DIR = "daoMouduleMapperDir";
    public static final String DAO_MOUDULE_DO_DIR = "daoMouduleDoDir";
    public static final String DAO_MOUDULE_MAPPER_XML_DIR = "daoMouduleMapperXmlDir";
    public static final String SERVICE_MOUDULE_SERVICE_DIR = "serviceMouduleServiceDir";
    public static final String SERVICE_MOUDULE_SERVICE_IMPL_DIR = "serviceMouduleServiceImplDir";
    public static final String SERVICE_MOUDULE_POJO_DIR = "serviceMoudulePojoDir";
    public static final String SERVICE_MOUDULE_DTO_DIR = "serviceMouduleDtoDir";
    public static final String SERVICE_MOUDULE_VO_DIR = "serviceMouduleVoDir";
    public static final String SERVICE_MOUDULE_QUERY_DIR = "serviceMouduleQueryDir";
    public static final String WEB_MOUDULE_CONTROLLER_DIR = "webMouduleControllerDir";
    public static final String WEB_MOUDULE_APPLICAITION_DIR = "webMouduleApplicationDir";
    public static final String WEB_MOUDULE_RESOURCE_DIR = "webMouduleResourceDir";

    private ProjectKey() {
        throw new IllegalStateException("常量类，禁止实例化");
    }
}
