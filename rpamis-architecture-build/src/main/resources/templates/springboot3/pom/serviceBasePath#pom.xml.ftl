<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>${project.groupId!}</groupId>
        <artifactId>${project.artifactId!}</artifactId>
        <version>${project.version!"1.0.0-SNAPSHOT"}</version>
    </parent>

    <artifactId>${project.artifactId!}-service</artifactId>
    <version>${project.version!"1.0.0-SNAPSHOT"}</version>
    <name>${project.artifactId!}-service</name>
    <description>${project.description!"Demo project for Spring Boot"}</description>
    <properties>
        <java.version>17</java.version>
    </properties>
<#if dependency??>
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
            <plugin>
                <artifactId>maven-assembly-plugin</artifactId>
                <version>2.4.1</version>
                <configuration>
                    <finalName>${project.artifactId}</finalName>
                    <descriptors>
                        <descriptor>src/main/assembly/distribution.xml</descriptor>
                    </descriptors>
                </configuration>
                <executions>
                    <execution>
                        <id>make-assembly</id>
                        <phase>package</phase>
                        <goals>
                            <goal>single</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
            <!-- 发布插件 deploy 时跳过该层 -->
            <plugin>
                <artifactId>maven-deploy-plugin</artifactId>
                <configuration>
                    <skip>true</skip>
                </configuration>
            </plugin>
        </plugins>

        <resources>
            <resource>
                <directory>src/main/resources</directory>
                <filtering>true</filtering>
            </resource>
        </resources>
        <testResources>
            <testResource>
                <directory>src/test/resources</directory>
                <filtering>true</filtering>
            </testResource>
        </testResources>
    </build>
</#if>

    <dependencies>

        <dependency>
            <groupId>${project.groupId!}</groupId>
            <artifactId>${project.artifactId!}-api</artifactId>
            <version>${project.version!"1.0.0-SNAPSHOT"}</version>
        </dependency>

<#if dependency??>
    <#if dependency.springcloud.enabled==true>
        <!--spring cloud -->

    </#if>
</#if>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-configuration-processor</artifactId>
        </dependency>

        <dependency>
            <groupId>com.fasterxml.jackson.datatype</groupId>
            <artifactId>jackson-datatype-jsr310</artifactId>
            <scope>compile</scope>
        </dependency>

        <!--test-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
        </dependency>

<#if dependency??>
    <#if dependency.database.enabled==true>
        <!-- mysql 相关 -->

    </#if>
    <#if dependency.kafka.enabled==true>
        <!-- kafka 相关 -->

    </#if>
    <#if dependency.redis.enabled==true>

    </#if>
    <#if dependency.mongoDb.enabled==true>
        <!-- mongoDb 相关 -->
    </#if>
    <#if dependency.exception.enabled==true>
        <!-- rpamis-exception全局异常 -->
    </#if>
    <#if dependency.security.enabled==true>
        <!-- rpamis-security安全组件 -->
    </#if>
    <#if dependency.healthCheck.enabled==true>
        <!-- rpamis-healthcheck健康检查组件 -->
    </#if>
</#if>
        <dependency>
            <groupId>com.google.guava</groupId>
            <artifactId>guava</artifactId>
            <exclusions>
                <exclusion>
                    <artifactId>checker-qual</artifactId>
                    <groupId>org.checkerframework</groupId>
                </exclusion>
                <exclusion>
                    <artifactId>error_prone_annotations</artifactId>
                    <groupId>com.google.errorprone</groupId>
                </exclusion>
            </exclusions>
        </dependency>
        <!-- 用于消除非空坏味道 -->
        <dependency>
            <groupId>org.jetbrains</groupId>
            <artifactId>annotations</artifactId>
            <version>13.0</version>
            <scope>compile</scope>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

</project>
