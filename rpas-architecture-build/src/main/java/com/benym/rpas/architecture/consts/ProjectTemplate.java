package com.benym.rpas.architecture.consts;

/**
 * @date 2022/7/26 4:41 下午
 * @author benym
 */
public final class ProjectTemplate {

    public static final String APPLICATION_YAML_NAME = "yamlResourcePath#application.yaml.ftl";

    public static final String BOOTSTRAP_YAML_NAME = "yamlResourcePath#bootstrap.yaml.ftl";

    public static final String APPLICATION_JAVA_NAME = "webPackagePath#Application.java.ftl";

    public static final String ROOT_POM_NAME = "rootBasePath#pom.xml.ftl";

    public static final String API_POM_NAME = "apiBasePath#pom.xml.ftl";

    public static final String DAO_POM_NAME = "daoBasePath#pom.xml.ftl";

    public static final String SERVICE_POM_NAME = "serviceBasePath#pom.xml.ftl";

    public static final String WEB_POM_NAME = "webBasePath#pom.xml.ftl";

    private ProjectTemplate() {
        throw new IllegalStateException("常量类，禁止实例化");
    }
}
