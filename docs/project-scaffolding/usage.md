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
import com.rpamis.architecture.config.BaseProjectConfig;
import com.rpamis.architecture.consts.TemplateTypeEnum;
import com.rpamis.architecture.pojo.Project;
import com.rpamis.architecture.pojo.Dependency;
import com.rpamis.architecture.controller.ArchitectureBuildController;
import com.rpamis.architecture.service.BuildService;
import com.rpamis.architecture.service.impl.BuildServiceImpl;
import com.rpamis.architecture.pojo.FileVO;

public class ProjectGenerator {

    public static void main(String[] args) {
        // 配置项目基本信息
        Project project = Project.builder()
                .groupId("com.example")
                .artifactId("my-project")
                .version("1.0.0")
                .packageName("com.example.myproject")
                .description("我的示例项目")
                .build();

        Dependency dependency = new Dependency();
        // 可以配置依赖信息，如consul、feign、database等

        // 创建项目配置
        BaseProjectConfig config = BaseProjectConfig.builder()
                .project(project)
                .dependency(dependency)
                .templateType(TemplateTypeEnum.MULTI_MOUDULE) // 多模块项目
                .build();

        // 生成项目
        BuildService buildService = new BuildServiceImpl();
        FileVO fileVO = buildService.architectureBuild(config);

        System.out.println("项目生成成功！下载ID：" + fileVO.getId());
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
| project | Project | 项目基本信息 |
| dependency | Dependency | 项目依赖配置 |
| templateType | TemplateTypeEnum | 项目类型（单模块/多模块/Starter） |

### 支持的项目类型

```java
import com.rpamis.architecture.consts.TemplateTypeEnum;

public enum TemplateTypeEnum {
    MULTI_MOUDULE("MULTI", "多模块项目"),
    SINGLE_MOUDULE("SINGLE", "单模块项目"),
    STARTER("STARTER", "Starter项目");
}
```

### 项目基本信息配置（Project类）

| 属性 | 类型 | 描述 |
| --- | --- | --- |
| groupId | String | 项目组织ID |
| artifactId | String | 项目ID |
| type | String | 项目类型（默认maven） |
| packaging | String | 打包方式（jar或war） |
| javaVersion | String | Java版本 |
| version | String | 项目版本 |
| packageName | String | 包名 |
| description | String | 项目描述 |
| mainName | String | 主类名（由artifactId自动转化生成） |

### 依赖配置（Dependency类）

| 属性 | 类型 | 描述 |
| --- | --- | --- |
| consul | Consul | Consul配置 |
| feign | Feign | Feign配置 |
| database | Database | 数据库配置 |

## 常见问题

### 1. 项目生成失败怎么办？

- 确保groupId、artifactId、packageName等必填字段不为空
- 检查artifactId是否符合命名规范（仅能以英文字母开头、英文字母结尾，可包含字母、数字、-线）
- 确保项目输出路径有写入权限

### 2. 如何选择项目类型？

- 对于简单的项目或原型开发，选择单模块项目（SINGLE_MOUDULE）
- 对于大型项目或需要模块化架构的项目，选择多模块项目（MULTI_MOUDULE）
- 如果您需要创建自定义的Spring Boot Starter，选择STARTER项目类型

### 3. 如何配置数据库？

在Dependency类中配置Database对象，支持常见的数据库类型（如MySQL、PostgreSQL等）。

### 4. 如何指定项目模板？

您可以通过 `ProjectTemplate` 类来指定自定义模板，或者使用预定义的模板。

### 5. 如何添加自定义模块？

在 `BaseProjectConfig` 的 `modules` 属性中添加您想要的模块名称列表。

### 6. 项目生成后如何配置？

项目生成后，您可以根据需要修改 `pom.xml` 和其他配置文件。