package com.benym.rpas.architecture;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author benym
 */
@ComponentScan(value = "com.benym.rpas")
@SpringBootApplication
public class RpasArchitectureBuildApplication {

    public static void main(String[] args) {
        SpringApplication.run(RpasArchitectureBuildApplication.class, args);
    }

}
