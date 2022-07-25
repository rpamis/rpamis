package com.benym.rpas.architecture.consts;

import java.io.File;

/**
 * @date 2022/7/25 10:40 上午
 */
public interface ProjectPath {

    String BUILD_PATH = System.getProperty("user.dir") + File.separator;

    String CACHETEMP_PATH = BUILD_PATH + "project_cache" + File.separator;

    String SRC_PATH = BUILD_PATH + "src" + File.separator;

    String MAIN_PATH = SRC_PATH + "main" + File.separator;

    String JAVA_PATH = MAIN_PATH + "java" + File.separator;

    String RESOURCE_PATH = MAIN_PATH + "resources" + File.separator;

    String TEMPLATES_PATH = RESOURCE_PATH + "templates" + File.separator;

    String YAML_PATH = TEMPLATES_PATH + "yaml" + File.separator;

    String CRUD_PATH = TEMPLATES_PATH + "crud" + File.separator;

    String POM_PATH = TEMPLATES_PATH + "pom" + File.separator;

    String PRACTICE_PATH = TEMPLATES_PATH + "practice" + File.separator;

    String APPLICATION_YAML_NAME = "application.yaml.ftl";

    String BOOTSTRAP_YAML_NAME = "bootstrap.yaml.ftl";
}
