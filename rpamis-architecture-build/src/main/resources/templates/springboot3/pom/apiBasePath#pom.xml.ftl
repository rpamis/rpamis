<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>${project.groupId!}</groupId>
        <artifactId>${project.artifactId!}</artifactId>
        <version>${project.version!"1.0.0-SNAPSHOT"}</version>
    </parent>

    <artifactId>${project.artifactId!}-api</artifactId>
    <version>${project.version!"1.0.0-SNAPSHOT"}</version>
    <name>${project.artifactId!}-api</name>
    <description>${project.description!"Demo project for Spring Boot"}</description>
    <properties>
        <java.version>17</java.version>
    </properties>
    <dependencies>
        <dependency>
            <groupId>${project.groupId!}</groupId>
            <artifactId>${project.artifactId!}-domain</artifactId>
            <version>${project.version!"1.0.0-SNAPSHOT"}</version>
        </dependency>
    </dependencies>

</project>
