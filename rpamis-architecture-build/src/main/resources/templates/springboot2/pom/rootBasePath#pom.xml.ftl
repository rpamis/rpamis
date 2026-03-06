<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>cn.rpamis</groupId>
        <artifactId>rpamis-boot-starter-parent</artifactId>
        <version>1.0.0.RELEASE</version>
        <relativePath/>
    </parent>

    <groupId>${project.groupId!}</groupId>
    <artifactId>${project.artifactId!}</artifactId>
    <version>${project.version!"1.0.0-SNAPSHOT"}</version>
    <packaging>pom</packaging>
    <name>${project.artifactId!}</name>
    <description>${project.description!"Demo project for Spring Boot"}</description>

    <modules>
<#if templateType??>
<#if templateType=='WEB_MOUDULE'>
        <module>${project.artifactId!}-web</module>
</#if>
</#if>
        <module>${project.artifactId!}-service</module>
        <module>${project.artifactId!}-dao</module>
        <module>${project.artifactId!}-api</module>
    </modules>


</project>
