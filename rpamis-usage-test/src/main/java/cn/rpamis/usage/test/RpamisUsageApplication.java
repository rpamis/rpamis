package cn.rpamis.usage.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author benym
 */
@ComponentScan(value = "cn.rpamis")
@SpringBootApplication
public class RpamisUsageApplication {

    public static void main(String[] args) {
        SpringApplication.run(RpamisUsageApplication.class, args);
    }

}
