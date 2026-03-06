# Rpamis 模块详细说明

本文档详细介绍 Rpamis 各个模块的架构、功能和设计理念。

## 模块概览

Rpamis 采用 Maven 多模块架构，共包含 13 个核心模块，分为以下几类：
- **基础依赖模块**：提供核心依赖管理和基础工具
- **Spring Boot Starters**：提供开箱即用的自动配置
- **架构与扩展模块**：提供项目架构生成和扩展能力

## 1. 基础依赖模块

### rpamis-boot-starter-parent

#### 功能定位
Spring Boot 父项目依赖管理，提供统一的依赖版本控制。

#### 核心特性
- 统一管理 Spring Boot 和 Spring Cloud 版本
- 配置常用依赖的版本
- 提供发布配置（Sonatype OSSRH）
- GPG 签名配置
- 代码格式化和覆盖率检查

#### 关键配置
```xml
<properties>
    <java.version>17</java.version>
    <spring-boot.version>3.4.9</spring-boot.version>
    <spring-cloud.version>2025.1.0-M1</spring-cloud.version>
    <!-- 其他依赖版本... -->
</properties>
```

### rpamis-common-dto

#### 功能定位
通用数据传输对象定义，提供统一的 API 响应格式和数据结构。

#### 核心组件

| 类名 | 功能 |
|------|------|
| **Response** | 统一响应类，支持成功/失败响应 |
| **PageResponse** | 分页响应类，支持分页查询结果返回 |
| **RequestLog** | HTTP 请求日志对象 |
| **Trace** | 分布式追踪数据对象 |

#### 核心特性
- 类型安全的响应类支持
- 统一的响应格式（success/code/message/details/data）
- 支持分页查询响应
- 响应码和状态码常量定义

### rpamis-common-exception

#### 功能定位
通用异常处理机制，提供层次化的异常体系。

#### 异常类型

| 异常类 | 继承关系 | 功能 |
|-------|---------|------|
| **BizException** | RuntimeException | 业务异常，无堆栈信息 |
| **SysException** | RuntimeException | 系统异常，有完整堆栈信息 |
| **ValidException** | RuntimeException | 验证异常，用于参数验证失败 |
| **BizNoStackException** | BizException | 无堆栈的业务异常（高性能） |

#### 核心特性
- 层次化异常体系
- 异常工厂模式创建异常实例
- 支持异常链和详细信息
- 高性能异常（无堆栈）支持

### rpamis-common-trace

#### 功能定位
分布式追踪与日志工具，提供请求响应日志记录和链路追踪。

#### 核心组件

| 类名 | 功能 |
|------|------|
| **TraceFilter** | Servlet 过滤器，自动记录请求响应日志 |
| **TraceDecorator** | 请求响应装饰器，增强日志内容 |
| **TraceStrategy** | 追踪策略接口，支持 Rpamis 和 SkyWalking |
| **TraceLogUtil** | 追踪日志工具类 |

#### 核心特性
- 自动请求响应日志记录
- 支持 SkyWalking 分布式追踪
- 统一的追踪上下文管理
- MDC（Mapped Diagnostic Context）集成

### rpamis-common-trace-toolkit

#### 功能定位
追踪工具包，提供对 SkyWalking 等分布式追踪系统的支持。

#### 核心组件
- SkyWalking 追踪策略实现
- 追踪上下文传递
- 链路追踪信息增强

#### 依赖关系
依赖于 `rpamis-common-trace` 模块，提供更强大的追踪功能。

### rpamis-common-utils

#### 功能定位
通用工具类集合，提供常用工具方法。

#### 核心工具类

| 类名 | 功能 |
|------|------|
| **SnowflakeUtil** | 雪花ID生成器 |
| **RpamisBeanUtil** | Bean 属性操作工具 |
| **FileUtil** | 文件操作工具 |
| **JackSonUtil** | JSON 序列化/反序列化工具 |
| **StringUtils** | 字符串工具 |
| **CollectionUtils** | 集合工具 |

#### 核心特性
- 无依赖的轻量级工具类
- 高性能实现
- 与常见工具库（Hutool、Apache Commons）互补

## 2. Spring Boot Starters

### rpamis-exception-spring-boot-starter

#### 功能定位
异常处理 Spring Boot 自动配置，提供开箱即用的异常处理能力。

#### 核心配置
```yaml
rpamis:
  exception:
    enabled: true              # 是否启用自动配置
    print-stack-trace: false   # 是否打印堆栈信息（对 BizException）
```

#### 核心特性
- 自动配置异常处理组件
- 统一的异常响应格式
- 支持自定义异常处理
- 与统一响应模块集成

### rpamis-enum-spring-boot-starter

#### 功能定位
可缓存枚举类型 Spring Boot 自动配置，提供枚举自动扫描和缓存。

#### 核心配置
```yaml
rpamis:
  enum:
    enabled: true              # 是否启用自动配置
    scan-packages: com.example # 枚举扫描包路径
```

#### 核心特性
- 自动扫描并缓存枚举类
- 提供枚举查找服务
- 支持自定义扫描路径
- 与 Spring Boot 环境集成

## 3. 架构与扩展模块

### rpamis-architecture-build

#### 功能定位
项目架构生成与构建工具，提供自动化的项目结构生成。

#### 核心组件

| 类名 | 功能 |
|------|------|
| **ArchitectureBuildController** | 项目生成控制器，提供 API 接口 |
| **ProjectTemplateManager** | 项目模板管理 |
| **CodeGenerator** | 代码生成工具 |
| **ConfigManager** | 配置管理 |

#### 核心特性
- 支持单模块和多模块项目生成
- 提供多种项目模板（Web、API、Starter等）
- 自动化的依赖配置
- 代码格式化支持

### rpamis-enum-core

#### 功能定位
可缓存枚举类型核心库，提供高性能的枚举管理。

#### 核心接口

| 接口名 | 功能 |
|-------|------|
| **CachableEnum** | 可缓存枚举接口，定义 code 和 desc 属性 |
| **EnumLookup** | 枚举查找服务接口 |
| **EnumCacheBuilder** | 枚举缓存构建器 |

#### 核心特性
- 高性能的枚举查找（O(1) 时间复杂度）
- 自动扫描和缓存枚举
- 支持多种查找方式（byCode、byDesc等）
- 并发安全的缓存实现

### rpamis-exception-dto

#### 功能定位
异常数据传输对象，提供异常信息的标准化表示。

#### 核心组件
- **ExceptionInfo**：异常信息对象，包含异常类型、消息、堆栈等
- **ExceptionResult**：异常处理结果对象
- **ValidationError**：验证错误信息对象

#### 核心特性
- 标准化的异常信息表示
- 支持异常链信息
- 与统一响应模块集成

### rpamis-extension-spi

#### 功能定位
SPI（服务提供者接口）框架，提供灵活的插件化扩展机制。

#### 核心组件

| 类名/注解 | 功能 |
|----------|------|
| **@RpamisSpi** | SPI 接口标记注解 |
| **SpiLoader** | SPI 加载器 |
| **PluginInjector** | 插件注入器 |
| **UniversalContainer** | 通用容器，管理 SPI 实例 |

#### 核心特性
- 简单的 SPI 接口定义（通过注解）
- 支持多种加载策略（内部/外部/原生）
- 自适应插件注入
- 线程安全的实例管理

### rpamis-extension-aspect

#### 功能定位
扩展切面支持，提供声明式的切面编程能力。

#### 核心特性
- 自定义注解支持的切面
- 方法拦截和增强
- 与 AOP 集成
- 支持多种连接点类型

## 4. 架构设计理念

### 1. 模块化设计
- 各个功能独立成模块，易于集成和维护
- 清晰的模块边界和依赖关系
- 支持按需引入，减少依赖冗余

### 2. 自动配置
- Spring Boot Starter 支持开箱即用
- 约定优于配置，降低学习成本
- 提供合理的默认配置

### 3. 可扩展性
- SPI 机制允许用户扩展功能
- 统一的扩展接口设计
- 插件化架构支持

### 4. 性能优化
- 无堆栈异常支持（提高业务异常处理性能）
- 高效的枚举查找（O(1) 时间复杂度）
- 线程安全的缓存实现

### 5. 可追踪性
- 完整的日志和追踪支持
- 统一的请求响应日志
- 支持分布式追踪系统集成

## 5. 模块依赖关系

```
rpamis-boot-starter-parent
├── rpamis-common-dto
├── rpamis-common-exception
├── rpamis-common-trace
│   └── rpamis-common-trace-toolkit
├── rpamis-common-utils
├── rpamis-enum-core
│   └── rpamis-enum-spring-boot-starter
├── rpamis-exception-dto
│   └── rpamis-exception-spring-boot-starter
├── rpamis-extension-spi
├── rpamis-extension-aspect
└── rpamis-architecture-build
```

## 6. 使用建议

### 1. 基础项目依赖
```xml
<!-- 父项目依赖 -->
<parent>
    <groupId>com.rpamis</groupId>
    <artifactId>rpamis-boot-starter-parent</artifactId>
    <version>1.0.2</version>
</parent>

<!-- 主要依赖 -->
<dependencies>
    <!-- 统一响应和异常处理 -->
    <dependency>
        <groupId>com.rpamis</groupId>
        <artifactId>rpamis-exception-spring-boot-starter</artifactId>
    </dependency>

    <!-- 可缓存枚举 -->
    <dependency>
        <groupId>com.rpamis</groupId>
        <artifactId>rpamis-enum-spring-boot-starter</artifactId>
    </dependency>

    <!-- 通用工具 -->
    <dependency>
        <groupId>com.rpamis</groupId>
        <artifactId>rpamis-common-utils</artifactId>
    </dependency>
</dependencies>
```

### 2. 架构生成工具使用
```java
// 使用架构生成工具创建新项目
ArchitectureBuildController controller = new ArchitectureBuildController();
controller.buildMultiModuleProject("my-project", "com.example", "1.0.0");
```

## 总结

Rpamis 提供了完整的基础设施库，帮助开发者快速构建高质量的 Java 应用程序。其模块化设计、自动配置和可扩展性，使得开发者可以专注于业务逻辑实现，而不必重复开发通用功能。

通过使用 Rpamis，团队可以实现：
1. 统一的开发规范和代码风格
2. 提高开发效率和代码质量
3. 减少重复开发和维护成本
4. 更好的系统可维护性和可扩展性