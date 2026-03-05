# Rpamis 🚀

[![Maven Central](https://img.shields.io/maven-central/v/com.rpamis/rpamis.svg?style=flat-square)](https://search.maven.org/search?q=g:com.rpamis)
[![GitHub Actions](https://github.com/rpamis/rpamis/workflows/full-build/badge.svg?branch=master)](https://github.com/rpamis/rpamis/actions)
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg?style=flat-square)](https://www.apache.org/licenses/LICENSE-2.0.html)
[![Java Version](https://img.shields.io/badge/Java-17+-blue.svg?style=flat-square)](https://adoptium.net/)

## 📖 介绍

**R**apid **P**roject **A**rchitecture and **M**icro **I**nfrastructure **S**ervice(RPAMIS)，快速项目架构及微型基建服务。

为开发者提供快速、统一的项目结构生成，统一的包管理工具，并提供开箱即用的开发效能工具。

## ✨ 核心功能

### 🚀 项目脚手架
- **rpamis-architecture-build** - 快速生成 Spring Boot 多模块 Maven 项目
- 支持单模块、多模块项目结构生成
- 自动化配置项目依赖和架构
- 提供完整的开发和构建脚本

### 🛡️ 异常处理体系
- **rpamis-exception-spring-boot-starter** - Exception Starter，自动配置异常处理
- **rpamis-common-exception** - 核心异常处理模块
  - 提供自定义校验器
  - 支持多种类型异常处理（业务异常、系统异常、验证异常等）
  - 全局异常处理
  - Dubbo 异常处理 Filter
  - 统一的异常响应格式

### 📊 枚举缓存机制
- **rpamis-enum-core** - 实现枚举缓存功能
- **CachableEnum 接口** - 统一的枚举定义方式
- **EnumLookup SDK** - 内置枚举查找工具，通过 Key 直接获取 Value
- 避免在枚举中重复编写获取枚举值的代码

### 🔌 SPI 扩展机制
- **rpamis-extension-spi** - RPAMIS 提供的 SPI 插件包
- **@RpamisSpi 注解** - 用于标注 SPI 接口
- **SPI 配置文件** - 在 `resource/META-INFO/rpamis` 目录中创建配置文件，格式：`接口名称=实现类全路径`（如：`customStrategy=com.xxx.CustomStrategy`）
- **支持多种注入方式** - SPI 实现类中支持纯 Java 和 Spring Bean 的注入
- **替代系统核心内容** - 方便用户使用 SPI 模式替代自己系统中的核心功能

### 🎯 其他核心功能
- 🔍 **分布式追踪** - 自动记录请求响应日志，支持链路追踪
- 🛠️ **通用工具库** - 提供常用工具方法（雪花ID、Bean操作等）
- 🌟 **Spring Boot 支持** - 开箱即用的自动配置

## 🚀 快速开始

### 📦 依赖引入

```xml
<dependency>
    <groupId>com.rpamis</groupId>
    <artifactId>rpamis-exception-spring-boot-starter</artifactId>
    <version>1.0.2</version>
</dependency>
```

### 🔄 统一响应使用

```java
import com.rpamis.common.dto.response.Response;

@RestController
@RequestMapping("/api")
public class DemoController {

    @GetMapping("/data")
    public Response<String> getData() {
        return Response.success("Hello, RPAMIS!");
    }

    @PostMapping("/save")
    public Response<Void> saveData(@RequestBody DataRequest request) {
        try {
            // 业务逻辑
            return Response.success();
        } catch (Exception e) {
            return Response.fail("ERROR_CODE", "保存失败");
        }
    }
}
```

### ⚠️ 异常处理示例

```java
import com.rpamis.common.exception.BizException;
import com.rpamis.common.exception.ExceptionFactory;

@Service
public class DemoService {

    public void doBusiness() {
        try {
            // 业务逻辑
            if (someCondition) {
                throw new BizException("业务操作失败", "详细信息");
            }
        } catch (Exception e) {
            throw ExceptionFactory.sysException("系统异常", e);
        }
    }
}
```

### 📊 枚举使用示例

```java
import com.rpamis.enumcore.common.CachableEnum;
import com.rpamis.enumcore.EnumLookup;

// 1. 定义枚举类并实现 CachableEnum 接口
public enum OrderStatusEnum implements CachableEnum<Integer, String> {
    PENDING(1, "待支付"),
    PAID(2, "已支付"),
    SHIPPED(3, "已发货"),
    DELIVERED(4, "已收货"),
    CANCELLED(5, "已取消");

    private final Integer code;
    private final String desc;

    OrderStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }
}

// 2. 在代码中使用 EnumLookup 直接获取枚举值
public class OrderService {

    public String getOrderStatusDesc(Integer status) {
        // 直接通过 EnumLookup 获取枚举值，无需在枚举中反复编写获取代码
        OrderStatusEnum statusEnum = EnumLookup.findEnumByCode(OrderStatusEnum.class, status);
        return statusEnum != null ? statusEnum.getDesc() : "未知状态";
    }
}
```

### 🔌 SPI 使用示例

```java
import com.rpamis.extension.spi.RpamisSpi;
import org.springframework.stereotype.Component;

// 1. 定义 SPI 接口并使用 @RpamisSpi 注解
@RpamisSpi
public interface CustomStrategy {
    String execute(String param);
}

// 2. 实现 SPI 接口（支持纯 Java 或 Spring Bean 注入）
@Component // 如果是 Spring Bean 注入，需要添加此注解
public class DefaultStrategy implements CustomStrategy {
    @Override
    public String execute(String param) {
        return "Default strategy: " + param;
    }
}

// 3. 在 resource/META-INFO/rpamis 目录下创建配置文件
// 文件名称：com.example.CustomStrategy
// 文件内容：
// default=com.example.DefaultStrategy

// 4. 在代码中使用 SPI
import com.rpamis.extension.spi.SpiLoader;

public class StrategyClient {
    public static void main(String[] args) {
        CustomStrategy strategy = SpiLoader.getLoader(CustomStrategy.class).getExtension("default");
        String result = strategy.execute("test parameter");
        System.out.println(result); // 输出：Default strategy: test parameter
    }
}
```

## 🏗️ 模块架构

RPAMIS 采用 Maven 多模块架构，主要模块包括：

### 📋 核心依赖模块
| 模块名称 | 主要功能 | 类型 |
|---------|---------|------|
| **rpamis-boot-starter-parent** | Spring Boot 父项目依赖管理 | 基础模块 |
| **rpamis-common-dto** | 通用数据传输对象定义（Response、Request等） | 核心库 |
| **rpamis-common-exception** | 通用异常处理机制（BizException、SysException等），提供自定义校验器、多种类型异常处理、全局异常处理、Dubbo异常处理Filter | 核心库 |
| **rpamis-common-trace** | 分布式追踪与日志工具 | 核心库 |
| **rpamis-common-trace-toolkit** | 追踪工具包（支持 SkyWalking） | 核心库 |
| **rpamis-common-utils** | 通用工具类集合（雪花ID、Bean工具、JSON工具等） | 核心库 |

### ⚡ Spring Boot Starters
| 模块名称 | 主要功能 | 类型 |
|---------|---------|------|
| **rpamis-exception-spring-boot-starter** | Exception 的 Starter，自动配置异常处理，核心依赖 rpamis-common-exception | Starter |
| **rpamis-enum-spring-boot-starter** | 可缓存枚举自动配置 | Starter |

### 🔧 架构与扩展模块
| 模块名称 | 主要功能 | 类型 |
|---------|---------|------|
| **rpamis-architecture-build** | 项目脚手架，用于快速生成 Spring Boot 多模块 Maven 项目 | 架构工具 |
| **rpamis-enum-core** | 实现枚举缓存，提供 CachableEnum 接口，通过 EnumLookup 直接获取枚举 Key 对应的 Value | 核心库 |
| **rpamis-exception-dto** | 异常数据传输对象 | 核心库 |
| **rpamis-extension-spi** | RPAMIS 提供的 SPI 插件包，支持用户用 @RpamisSpi 标注接口，实现接口后在 resource/META-INFO/rpamis 目录中配置，支持纯 Java 和 Spring Bean 的注入 | 扩展库 |
| **rpamis-extension-aspect** | 扩展切面支持 | 扩展库 |

## 📚 详细文档

- 📖 [使用指南](docs/usage.md) - 详细的功能使用说明
- 📚 [API 文档](https://rpamis.github.io/rpamis/) - 完整的 API 文档
- 📦 [模块说明](docs/modules.md) - 各模块详细功能介绍
- ❓ [常见问题](docs/faq.md) - 常见问题解答

## 🚀 快速架构生成

### 使用项目脚手架

rpamis-architecture-build 是一个强大的项目脚手架，用于快速生成 Spring Boot 的多模块 Maven 项目。

#### 1. 引入依赖

```xml
<dependency>
    <groupId>com.rpamis</groupId>
    <artifactId>rpamis-architecture-build</artifactId>
    <version>1.0.2</version>
</dependency>
```

#### 2. 使用 API 生成项目

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

#### 3. 支持的项目类型

- **单模块项目** - 适合小型项目快速开发
- **多模块项目** - 适合大型项目架构，包含业务模块、基础模块、API模块等
- **Spring Boot Starter** - 快速创建自定义的 Spring Boot Starter 项目

#### 4. 项目结构示例

生成的项目结构如下：

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

#### 5. 自动化配置

项目脚手架会自动配置：

- 统一的依赖管理
- 代码格式化工具
- 测试框架配置
- CI/CD 配置
- 常见开发工具集成

## 💻 系统要求

- **Java**: 17+ ☕
- **Spring Boot**: 3.4.9+ 🌱
- **Maven**: 3.6+ 📦

## 🔨 安装与构建

```bash
# 克隆项目
git clone https://github.com/rpamis/rpamis.git

# 进入项目目录
cd rpamis

# 构建项目（包括测试）
mvn clean install

# 跳过测试构建
mvn clean install -DskipTests

# 代码格式化
mvn spring-javaformat:apply
```

## ⚙️ 项目配置

### 📤 发布到 Maven Central

```bash
# 发布版本构建
mvn clean deploy -Prelease

# 快照版本构建
mvn clean deploy -Psonatype
```

## 📝 开发规范

### 🎨 代码风格
- 遵循 Spring 代码风格 ✨
- 使用 Maven 插件自动格式化 🛠️

### 📝 提交规范
- feat: 新功能 ✨
- fix: 修复bug 🐛
- docs: 文档变更 📚
- style: 代码格式 🎨
- refactor: 重构 🔄
- test: 测试 🧪
- chore: 构建或辅助工具的变更 🛠️

## 🤝 贡献指南

1. Fork 项目 🍴
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`) 🌿
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`) 💬
4. 推送到分支 (`git push origin feature/AmazingFeature`) 📤
5. 打开 Pull Request 🚀

## 📄 许可证

该项目采用 Apache License 2.0 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情。

## 📞 联系方式

- **项目主页**: https://github.com/rpamis/rpamis 🏠
- **Issue 跟踪**: https://github.com/rpamis/rpamis/issues 🐛
- **开发邮件**: benyuanming@gmail.com ✉️

## 🙏 致谢

感谢所有为 RPAMIS 做出贡献的开发者！

---

**RPAMIS** - 让开发变得更简单、更高效！ 🚀