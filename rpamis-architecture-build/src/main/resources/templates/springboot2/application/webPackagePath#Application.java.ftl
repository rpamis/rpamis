package ${project.packageName!};

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
<#if dependency??>
    <#if dependency.database.enabled==true>
import org.mybatis.spring.annotation.MapperScan;
    </#if>
    <#if dependency.consul??&&(dependency.consul.enabled==true)>
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
    </#if >
</#if>
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@EnableDubbo
<#if dependency??>
    <#if dependency.database.enabled==true>
@MapperScan("${project.packageName!}.dao")
    </#if>
    <#if dependency.feign??&&(dependency.feign.enabled==true)>
@EnableFeignClients
    </#if >
</#if>
@EnableAspectJAutoProxy(exposeProxy = true, proxyTargetClass = true)
@SpringBootApplication()
public class ${project.mainName!}Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(${project.mainName!}Application.class);

    public static void main(String[] args) {
        SpringApplication.run(${project.mainName!}Application.class, args);
        LOGGER.info("${project.artifactId!} started!!!");
    }

}
