package com.benym.rpas.architecture.consts;

/**
 * @date 2022/7/26 4:41 下午
 */
public interface ProjectTemplate {

    String APPLICATION_YAML_NAME = "yamlResourcePath#application.yaml.ftl";

    String BOOTSTRAP_YAML_NAME = "yamlResourcePath#bootstrap.yaml.ftl";

    String APPLICATION_JAVA_NAME = "webPackagePath#Application.java.ftl";

    String ROOT_POM_NAME = "rootBasePath#pom.xml.ftl";

    String API_POM_NAME = "apiBasePath#pom.xml.ftl";

    String DAO_POM_NAME = "daoBasePath#pom.xml.ftl";

    String SERVICE_POM_NAME = "serviceBasePath#pom.xml.ftl";

    String WEB_POM_NAME = "webBasePath#pom.xml.ftl";
}
