package cn.rpamis.architecture.consts;

import java.io.File;

/**
 * @date 2022/7/25 10:40 上午
 * @author benym
 */
public final class ProjectPath {

    public static final String BUILD_PATH = System.getProperty("user.dir") + File.separator;

    public static final String BUILD_PROJECT_NAME = "rpas-architecture-build" + File.separator;

    public static final String CACHETEMP_PATH = BUILD_PATH + "project_cache" + File.separator;

    public static final String COPYTEMPLATES_PATH = CACHETEMP_PATH + "templates" + File.separator;

    public static final String SRC_PATH = "src" + File.separator;

    public static final String MAIN_PATH = SRC_PATH + "main" + File.separator;

    public static final String JAVA_PATH = MAIN_PATH + "java" + File.separator;

    public static final String RESOURCE_PATH = MAIN_PATH + "resources" + File.separator;

    public static final String TEMPLATES_PATH = RESOURCE_PATH + "templates" + File.separator;

    public static final String YAML_PATH = TEMPLATES_PATH + "yaml" + File.separator;

    public static final String CRUD_PATH = TEMPLATES_PATH + "crud" + File.separator;

    public static final String POM_PATH = TEMPLATES_PATH + "pom" + File.separator;

    public static final String PRACTICE_PATH = TEMPLATES_PATH + "practice" + File.separator;

    public static final String APPLICATION_PATH = TEMPLATES_PATH + "application" + File.separator;

    public static final String POJO_PATH = "pojo" + File.separator;

    public static final String DTO_PATH = "dto" + File.separator;

    public static final String VO_PATH = "vo" + File.separator;

    public static final String DO_PATH = "domain" + File.separator;

    public static final String QUERY_PATH = "query" + File.separator;

    public static final String API_PATH = "api" + File.separator;

    public static final String CONFIG_PATH = "config" + File.separator;

    public static final String SERVICE_PATH = "service" + File.separator;

    public static final String SERVICE_IMPL_PATH = "impl" + File.separator;

    public static final String DAO_PATH = "dao" + File.separator;

    public static final String DAO_IMPL_PATH = "impl" + File.separator;

    public static final String MAPPER_PATH = "mapper" + File.separator;

    public static final String MAPPER_XML_PATH = "mapper" + File.separator;

    public static final String CONTROLLER_PATH = "controller" + File.separator;

    private ProjectPath() {
        throw new IllegalStateException("常量类，禁止实例化");
    }
}
