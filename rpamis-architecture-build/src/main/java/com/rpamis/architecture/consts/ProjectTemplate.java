package com.rpamis.architecture.consts;

/**
 * 项目模板
 *
 * @author benym
 * @date 2022/7/26 4:41 下午
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

	public static final String BOOTSTRAP_YML = "propertiesResourcePath#bootstrap.yml.ftl";

	public static final String MYBATISPLUS_CONFIG = "webConfigDir#MybatisPlusConfig.java.ftl";

	public static final String MONGO_CONFIG = "webConfigDir#MongoConfig.java.ftl";

	public static final String DUBBO_FILTER = "webDubboFilterDir#com.alibaba.dubbo.rpc.Filter.ftl";

	public static final String APACHE_DUBBO_FILTER = "webDubboFilterDir#org.apache.dubbo.rpc.Filter.ftl";

	public static final String THREAD_CONFIG = "webConfigDir#ThreadPoolExecutorConfig.java.ftl";

	public static final String JACKSON_CONFIG = "webConfigDir#JackSonConfig.java.ftl";

	public static final String REDISSION_CONFIG = "webConfigDir#RedissonConfig.java.ftl";

	public static final String RESTTEMPLATE_CONFIG = "webConfigDir#RestTemplateConfig.java.ftl";

	public static final String ASSEMBLY_XML_NAME = "assemblyPath#distribution.xml.ftl";

	private ProjectTemplate() {
		throw new IllegalStateException("常量类，禁止实例化");
	}

}
