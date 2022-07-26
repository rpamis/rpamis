package ${project.packageName!};

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

<#if dependency.consul??&&(dependency.consul.enabled==true)>
  import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
</#if >

@ComponentScan(basePackages={"${project.packageName!}"})
<#if dependency.feign??&&(dependency.feign.enabled==true)>
  @EnableFeignClients
</#if >
@SpringBootApplication
public class ${pathMap.applicationName!}Application {

    public static void main(String[] args) {
        SpringApplication.run(${pathMap.applicationName!}Application.class, args);
    }

}
