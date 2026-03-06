```
# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.
```

## 项目概述

**Rpamis (Rapid Project Architecture and Micro Infrastructure Service)** 是一个为Java开发者提供快速项目架构生成和开发效能工具的开源项目。该项目采用Maven多模块架构，提供了统一的异常处理、枚举缓存、SPI扩展机制和项目脚手架等核心功能。

## 核心功能模块

### 1. 项目脚手架 (`rpamis-architecture-build`)
- 快速生成Spring Boot多模块Maven项目
- 支持单模块、多模块和Spring Boot Starter项目类型
- 自动化配置项目依赖和架构
- 提供完整的开发和构建脚本

### 2. 异常处理体系 (`rpamis-exception-spring-boot-starter`)
- 自动配置异常处理
- 提供自定义校验器
- 支持多种类型异常处理（业务异常、系统异常、验证异常等）
- 全局异常处理和Dubbo异常处理Filter
- 统一的异常响应格式

### 3. 枚举缓存机制 (`rpamis-enum-core`)
- 实现枚举缓存功能
- 提供`CachableEnum`接口和`EnumLookup` SDK
- 支持通过Key直接获取Value，避免重复代码

### 4. SPI扩展机制 (`rpamis-extension-spi`)
- 提供SPI插件包
- 使用`@RpamisSpi`注解标注接口
- 配置文件格式：`META-INF/rpamis/接口名称=实现类全路径`
- 支持纯Java和Spring Bean注入方式

## 常用开发命令

### 项目构建
```bash
# 克隆项目
git clone https://github.com/rpamis/rpamis.git
cd rpamis

# 构建项目（包括测试）
mvn clean install

# 跳过测试构建
mvn clean install -DskipTests

# 仅编译
mvn compile
```

### 代码格式化
```bash
# 使用Spring JavaFormat插件格式化代码
mvn spring-javaformat:apply

# 验证代码格式
mvn spring-javaformat:validate
```

### 发布到Maven Central
```bash
# 发布版本构建
mvn clean deploy -Prelease

# 快照版本构建
mvn clean deploy -Psonatype
```

### 运行项目
```bash
# 运行项目脚手架应用
cd rpamis-architecture-build
mvn spring-boot:run
```

## 项目架构

### 模块层次结构
```
rpamis (根项目)
├── rpamis-boot-starter-parent (Spring Boot父项目依赖管理)
├── rpamis-common-dto (通用数据传输对象)
├── rpamis-common-exception (通用异常处理机制)
├── rpamis-common-trace (分布式追踪与日志工具)
├── rpamis-common-trace-toolkit (追踪工具包，支持SkyWalking)
├── rpamis-common-utils (通用工具类集合)
├── rpamis-architecture-build (项目脚手架)
├── rpamis-enum-core (枚举缓存核心库)
├── rpamis-enum-spring-boot-starter (可缓存枚举自动配置)
├── rpamis-exception-dto (异常数据传输对象)
├── rpamis-exception-spring-boot-starter (异常处理自动配置)
├── rpamis-extension-aspect (扩展切面支持)
└── rpamis-extension-spi (SPI扩展机制)
```

## 开发规范

### 代码风格
- 遵循Spring代码风格
- 使用Maven插件自动格式化
- 配置文件位置：`pom.xml`中的`spring-javaformat-maven-plugin`

### 提交规范
- `feat`: 新功能
- `fix`: 修复bug
- `docs`: 文档变更
- `style`: 代码格式
- `refactor`: 重构
- `test`: 测试
- `chore`: 构建或辅助工具的变更

## 技术栈

- **Java**: 17+
- **Spring Boot**: 3.4.9+
- **Maven**: 3.6+
- **数据库**: 支持多种数据库（通过配置）
- **服务注册**: Consul（可选）
- **分布式追踪**: SkyWalking（可选）

## 关键API

### 异常处理
```java
// 创建业务异常
throw ExceptionFactory.bizException("业务异常信息");

// 创建系统异常
throw ExceptionFactory.sysException("系统异常信息", throwable);

// 创建验证异常
throw ExceptionFactory.validException("验证失败信息");
```

### 枚举缓存
```java
// 定义枚举
public enum OrderStatusEnum implements CachableEnum<Integer, String> {
    PENDING(1, "待支付"),
    PAID(2, "已支付"),
    SHIPPED(3, "已发货");

    private final Integer code;
    private final String desc;

    // 构造方法和实现方法
}

// 使用EnumLookup查找枚举
OrderStatusEnum status = EnumLookup.getEnumByCode(OrderStatusEnum.class, 1);
```

### SPI扩展
```java
// 定义SPI接口
@RpamisSpi
public interface CustomStrategy {
    String execute(String param);
}

// 实现SPI接口
public class DefaultStrategy implements CustomStrategy {
    @Override
    public String execute(String param) {
        return "Default strategy: " + param;
    }
}

// 使用SPI
CustomStrategy strategy = SpiLoader.getSpiLoader(CustomStrategy.class).getSpiImpl("default");
```

## 文档结构

项目文档位于`docs/`目录下，按功能模块分类：
- `docs/exception-handling/usage.md` - 异常处理使用指南
- `docs/enum-cache/usage.md` - 枚举缓存使用指南
- `docs/project-scaffolding/usage.md` - 项目脚手架使用指南
- `docs/spi-extension/usage.md` - SPI扩展机制使用指南
- `docs/modules.md` - 模块架构说明
- `docs/faq.md` - 常见问题解答

## 测试

项目使用JUnit进行单元测试，运行测试命令：
```bash
# 运行所有测试
mvn test

# 运行特定模块的测试
cd 模块名称
mvn test

# 查看测试覆盖率（使用JaCoCo）
mvn jacoco:report
```

## 注意事项

1. 项目使用Maven profile管理版本，发布时使用`-Prelease`或`-Psonatype`
2. 所有模块都继承自`rpamis-boot-starter-parent`
3. 代码格式化使用Spring JavaFormat插件
4. 项目支持分布式追踪，通过配置可集成SkyWalking
