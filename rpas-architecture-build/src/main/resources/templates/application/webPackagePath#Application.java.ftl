package ${project.packageName!};

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

<#if dependency??>
<#if dependency.consul??&&(dependency.consul.enabled==true)>
  import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
</#if >

//@ComponentScan(basePackages={"${project.packageName!}"})
<#if dependency.feign??&&(dependency.feign.enabled==true)>
  @EnableFeignClients
</#if >
</#if>
@SpringBootApplication
public class ${project.mainName!}Application {

    public static void main(String[] args) {
        SpringApplication.run(${project.mainName!}Application.class, args);
    }

}
