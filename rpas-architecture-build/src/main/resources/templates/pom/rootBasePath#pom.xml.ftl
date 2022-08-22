<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>${project.groupId!}</groupId>
    <artifactId>${project.artifactId!}</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <packaging>pom</packaging>
    <name>${project.artifactId!}</name>
    <description>Demo project for Spring Boot</description>

    <modules>
        <module>${project.artifactId!}-web</module>
        <module>${project.artifactId!}-service</module>
        <module>${project.artifactId!}-dao</module>
        <module>${project.artifactId!}-api</module>
    </modules>

    <properties>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
    </properties>


</project>
