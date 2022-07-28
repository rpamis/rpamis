package com.benym.rpas.architecture.consts;

import java.io.File;

/**
 * @date 2022/7/25 10:40 上午
 */
public interface ProjectPath {

    String BUILD_PATH = System.getProperty("user.dir") + File.separator;

    String BUILD_PROJECT_NAME = "rpas-architecture-build" + File.separator;

    String CACHETEMP_PATH = BUILD_PATH + "project_cache" + File.separator;

    String SRC_PATH = "src" + File.separator;

    String MAIN_PATH = SRC_PATH + "main" + File.separator;

    String JAVA_PATH = MAIN_PATH + "java" + File.separator;

    String RESOURCE_PATH = MAIN_PATH + "resources" + File.separator;

    String TEMPLATES_PATH = RESOURCE_PATH + "templates" + File.separator;

    String YAML_PATH = TEMPLATES_PATH + "yaml" + File.separator;

    String CRUD_PATH = TEMPLATES_PATH + "crud" + File.separator;

    String POM_PATH = TEMPLATES_PATH + "pom" + File.separator;

    String PRACTICE_PATH = TEMPLATES_PATH + "practice" + File.separator;

    String APPLICATION_PATH = TEMPLATES_PATH + "application" + File.separator;

    String POJO_PATH = "pojo" + File.separator;

    String DTO_PATH = "dto" + File.separator;

    String VO_PATH = "vo" + File.separator;

    String DO_PATH = "domain" + File.separator;

    String QUERY_PATH = "query" + File.separator;

    String API_PATH = "api" + File.separator;

    String CONFIG_PATH = "config" + File.separator;

    String SERVICE_PATH = "service" + File.separator;

    String SERVICE_IMPL_PATH = "impl" + File.separator;

    String DAO_PATH = "dao" + File.separator;

    String DAO_IMPL_PATH = "impl" + File.separator;

    String MAPPER_PATH = "mapper" + File.separator;

    String MAPPER_XML_PATH = "mapper" + File.separator;

    String CONTROLLER_PATH = "controller" + File.separator;

}
