# 项目脚手架使用指南 (rpamis-architecture-build)

## 概述

rpamis-architecture-build 是一个强大的项目脚手架工具，用于快速生成 Spring Boot 多模块 Maven 项目。

## 功能特性

- 支持单模块、多模块项目结构生成
- 自动化配置项目依赖和架构
- 提供完整的开发和构建脚本
- 支持多种项目类型：
  - 单模块项目（适合小型项目快速开发）
  - 多模块项目（适合大型项目架构）
  - Spring Boot Starter（快速创建自定义的 Spring Boot Starter 项目）

## 快速开始

### 1. 引入依赖

```xml
<dependency>
    <groupId>com.rpamis</groupId>
    <artifactId>rpamis-architecture-build</artifactId>
    <version>1.0.2</version>
</dependency>
```

### 2. 使用 API 生成项目

```java
import com.rpamis.architecture.build.ArchitectureBuildController;
import com.rpamis.architecture.build.vo.BaseProjectConfig;

public class ProjectGenerator {

    public static void main(String[] args) {
        // 配置项目基本信息
        BaseProjectConfig config = new BaseProjectConfig();
        config.setGroupId("com.example");
        config.setArtifactId("my-project");
        config.setVersion("1.0.0");
        config.setPackageName("com.example.myproject");
        config.setDescription("我的示例项目");

        // 生成多模块项目
        ArchitectureBuildController controller = new ArchitectureBuildController();
        controller.buildMultiModuleProject(config);

        System.out.println("项目生成成功！");
    }
}
```

### 3. 项目结构示例

生成的多模块项目结构如下：

```
my-project/
├── my-project-common/        # 公共基础模块
├── my-project-dao/          # 数据访问模块
├── my-project-service/      # 业务逻辑模块
├── my-project-api/          # API接口模块
├── my-project-web/          # Web应用模块
├── my-project-starter/      # 自定义Starter
└── pom.xml                  # 父项目依赖管理
```

### 4. 自动化配置

项目脚手架会自动配置：

- 统一的依赖管理
- 代码格式化工具
- 测试框架配置
- CI/CD 配置
- 常见开发工具集成

## 详细用法

### 配置选项

BaseProjectConfig 类包含以下主要配置选项：

| 属性 | 类型 | 描述 |
| --- | --- | --- |
| groupId | String | 项目组织ID |
| artifactId | String | 项目ID |
| version | String | 项目版本 |
| packageName | String | 包名 |
| description | String | 项目描述 |
| projectType | ProjectType | 项目类型（单模块/多模块/Starter） |
| modules | List<String> | 自定义模块列表（多模块项目时使用） |

### 支持的项目类型

```java
public enum ProjectType {
    SINGLE_MODULE,    // 单模块项目
    MULTI_MODULE,     // 多模块项目
    SPRING_BOOT_STARTER // Spring Boot Starter
}
```

### 高级配置

您可以通过以下方式进行更高级的配置：

```java
import com.rpamis.architecture.build.vo.ProjectTemplate;

public class CustomProjectGenerator {
    public static void main(String[] args) {
        BaseProjectConfig config = new BaseProjectConfig();
        config.setGroupId("com.example");
        config.setArtifactId("custom-project");
        config.setVersion("1.0.0");
        config.setPackageName("com.example.customproject");
        config.setDescription("自定义项目架构");

        // 使用自定义模板
        ProjectTemplate customTemplate = new ProjectTemplate();
        customTemplate.setTemplateName("custom-template");
        customTemplate.setModuleNames(Arrays.asList("common", "dao", "service", "api"));

        ArchitectureBuildController controller = new ArchitectureBuildController();
        controller.buildProjectWithTemplate(config, customTemplate);
    }
}
```

## 常见问题

### 1. 如何指定项目模板？

您可以通过 `ProjectTemplate` 类来指定自定义模板，或者使用预定义的模板。

### 2. 如何添加自定义模块？

在 `BaseProjectConfig` 的 `modules` 属性中添加您想要的模块名称列表。

### 3. 项目生成后如何配置？

项目生成后，您可以根据需要修改 `pom.xml` 和其他配置文件。

## 参考链接

- [官方文档](https://github.com/rpamis/rpamis/wiki/Architecture-Build)
- [API 文档](https://rpamis.github.io/rpamis)