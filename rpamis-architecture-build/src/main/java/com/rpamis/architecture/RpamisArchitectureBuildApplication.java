package com.rpamis.architecture;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author benym
 */
@ComponentScan(value = "cn.rpamis")
@SpringBootApplication
public class RpamisArchitectureBuildApplication {

    public static void main(String[] args) {
        SpringApplication.run(RpamisArchitectureBuildApplication.class, args);
    }

}
