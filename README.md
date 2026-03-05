# Rpamis 🚀

[![Maven Central](https://img.shields.io/maven-central/v/com.rpamis/rpamis.svg?style=flat-square)](https://search.maven.org/search?q=g:com.rpamis)
[![GitHub Actions](https://github.com/rpamis/rpamis/workflows/full-build/badge.svg?branch=master)](https://github.com/rpamis/rpamis/actions)
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg?style=flat-square)](https://www.apache.org/licenses/LICENSE-2.0.html)
[![Java Version](https://img.shields.io/badge/Java-17+-blue.svg?style=flat-square)](https://adoptium.net/)

## 📖 介绍

**R**apid **P**roject **A**rchitecture and **M**icro **I**nfrastructure **S**ervice(RPAMIS)，快速项目架构及微型基建服务。

为开发者提供快速、统一的项目结构生成，统一的包管理工具，并提供开箱即用的开发效能工具。

## ✨ 功能特性

- 🎯 **统一响应格式** - 标准化 API 响应结构，支持成功/失败响应
- 🛡️ **异常体系** - 层次化异常处理，支持业务和系统异常分离
- 🔍 **分布式追踪** - 自动记录请求响应日志，支持链路追踪
- 📊 **可缓存枚举** - 高性能枚举管理，支持自动扫描和缓存
- 🔌 **SPI 扩展机制** - 灵活的插件化扩展框架
- 📦 **项目生成工具** - 自动化创建项目架构
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

## 🏗️ 模块架构

RPAMIS 采用 Maven 多模块架构，主要模块包括：

### 📋 核心依赖模块
| 模块名称 | 主要功能 | 类型 |
|---------|---------|------|
| **rpamis-boot-starter-parent** | Spring Boot 父项目依赖管理 | 基础模块 |
| **rpamis-common-dto** | 通用数据传输对象定义（Response、Request等） | 核心库 |
| **rpamis-common-exception** | 通用异常处理机制（BizException、SysException等） | 核心库 |
| **rpamis-common-trace** | 分布式追踪与日志工具 | 核心库 |
| **rpamis-common-trace-toolkit** | 追踪工具包（支持 SkyWalking） | 核心库 |
| **rpamis-common-utils** | 通用工具类集合（雪花ID、Bean工具、JSON工具等） | 核心库 |

### ⚡ Spring Boot Starters
| 模块名称 | 主要功能 | 类型 |
|---------|---------|------|
| **rpamis-exception-spring-boot-starter** | 异常处理自动配置 | Starter |
| **rpamis-enum-spring-boot-starter** | 可缓存枚举自动配置 | Starter |

### 🔧 架构与扩展模块
| 模块名称 | 主要功能 | 类型 |
|---------|---------|------|
| **rpamis-architecture-build** | 项目架构生成与构建工具 | 架构工具 |
| **rpamis-enum-core** | 可缓存枚举类型核心库 | 核心库 |
| **rpamis-exception-dto** | 异常数据传输对象 | 核心库 |
| **rpamis-extension-spi** | SPI（服务提供者接口）框架 | 扩展库 |
| **rpamis-extension-aspect** | 扩展切面支持 | 扩展库 |

## 📚 详细文档

- 📖 [使用指南](docs/usage.md) - 详细的功能使用说明
- 📚 [API 文档](https://rpamis.github.io/rpamis/) - 完整的 API 文档
- 📦 [模块说明](docs/modules.md) - 各模块详细功能介绍
- ❓ [常见问题](docs/faq.md) - 常见问题解答

## 🚀 快速架构生成

使用 rpamis-architecture-build 模块快速生成项目架构：

```java
// TODO: 示例代码
```

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