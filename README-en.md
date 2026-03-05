# Rpamis 🚀

[![Maven Central](https://img.shields.io/maven-central/v/com.rpamis/rpamis.svg?style=flat-square)](https://search.maven.org/search?q=g:com.rpamis)
[![GitHub Actions](https://github.com/rpamis/rpamis/workflows/full-build/badge.svg?branch=master)](https://github.com/rpamis/rpamis/actions)
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg?style=flat-square)](https://www.apache.org/licenses/LICENSE-2.0.html)
[![Java Version](https://img.shields.io/badge/Java-17+-blue.svg?style=flat-square)](https://adoptium.net/)

## 📖 Introduction

**R**apid **P**roject **A**rchitecture and **M**icro **I**nfrastructure **S**ervice(RPAMIS) - Rapid Project Architecture and Micro Infrastructure Service.

Provides developers with fast, unified project structure generation, unified package management tools, and ready-to-use development efficiency tools.

## ✨ Features

- 🎯 **Unified Response Format** - Standardized API response structure, supporting success/failure responses
- 🛡️ **Exception System** - Hierarchical exception handling, supporting separation of business and system exceptions
- 🔍 **Distributed Tracing** - Automatically records request and response logs, supporting distributed tracing
- 📊 **Cacheable Enums** - High-performance enum management, supporting automatic scanning and caching
- 🔌 **SPI Extension Mechanism** - Flexible plugin-based extension framework
- 📦 **Project Generation Tool** - Automatically creates project architectures
- 🛠️ **Common Utilities** - Provides commonly used tool methods (Snowflake ID, Bean operations, etc.)
- 🌟 **Spring Boot Support** - Out-of-the-box automatic configuration

## 🚀 Quick Start

### 📦 Dependency Introduction

```xml
<dependency>
    <groupId>com.rpamis</groupId>
    <artifactId>rpamis-exception-spring-boot-starter</artifactId>
    <version>1.0.2</version>
</dependency>
```

### 🔄 Using Unified Response

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
            // Business logic
            return Response.success();
        } catch (Exception e) {
            return Response.fail("ERROR_CODE", "Save failed");
        }
    }
}
```

### ⚠️ Exception Handling Example

```java
import com.rpamis.common.exception.BizException;
import com.rpamis.common.exception.ExceptionFactory;

@Service
public class DemoService {

    public void doBusiness() {
        try {
            // Business logic
            if (someCondition) {
                throw new BizException("Business operation failed", "Detailed information");
            }
        } catch (Exception e) {
            throw ExceptionFactory.sysException("System exception", e);
        }
    }
}
```

## 🏗️ Module Architecture

RPAMIS uses a Maven multi-module architecture, with the following main modules:

### 📋 Core Dependency Modules
| Module Name | Main Function | Type |
|------------|--------------|------|
| **rpamis-boot-starter-parent** | Spring Boot parent project dependency management | Base Module |
| **rpamis-common-dto** | Common data transfer object definitions (Response, Request, etc.) | Core Library |
| **rpamis-common-exception** | Common exception handling mechanism (BizException, SysException, etc.) | Core Library |
| **rpamis-common-trace** | Distributed tracing and logging tools | Core Library |
| **rpamis-common-trace-toolkit** | Tracing toolkit (supports SkyWalking) | Core Library |
| **rpamis-common-utils** | Common utility class collection (Snowflake ID, Bean tools, JSON tools, etc.) | Core Library |

### ⚡ Spring Boot Starters
| Module Name | Main Function | Type |
|------------|--------------|------|
| **rpamis-exception-spring-boot-starter** | Exception handling automatic configuration | Starter |
| **rpamis-enum-spring-boot-starter** | Cacheable enum automatic configuration | Starter |

### 🔧 Architecture and Extension Modules
| Module Name | Main Function | Type |
|------------|--------------|------|
| **rpamis-architecture-build** | Project architecture generation and construction tools | Architecture Tool |
| **rpamis-enum-core** | Cacheable enum type core library | Core Library |
| **rpamis-exception-dto** | Exception data transfer objects | Core Library |
| **rpamis-extension-spi** | SPI (Service Provider Interface) framework | Extension Library |
| **rpamis-extension-aspect** | Extension aspect support | Extension Library |

## 📚 Detailed Documentation

- 📖 [Usage Guide](docs/usage.md) - Detailed function usage instructions
- 📚 [API Documentation](https://rpamis.github.io/rpamis/) - Complete API documentation
- 📦 [Module Descriptions](docs/modules.md) - Detailed function descriptions for each module
- ❓ [FAQ](docs/faq.md) - Frequently Asked Questions

## 🚀 Rapid Architecture Generation

Use the rpamis-architecture-build module to quickly generate project architectures:

```java
// TODO: Example code
```

## 💻 System Requirements

- **Java**: 17+ ☕
- **Spring Boot**: 3.4.9+ 🌱
- **Maven**: 3.6+ 📦

## 🔨 Installation and Build

```bash
# Clone the project
git clone https://github.com/rpamis/rpamis.git

# Enter the project directory
cd rpamis

# Build the project (including tests)
mvn clean install

# Build skipping tests
mvn clean install -DskipTests

# Code formatting
mvn spring-javaformat:apply
```

## ⚙️ Project Configuration

### 📤 Publish to Maven Central

```bash
# Release version build
mvn clean deploy -Prelease

# Snapshot version build
mvn clean deploy -Psonatype
```

## 📝 Development Specifications

### 🎨 Code Style
- Follow Spring code style ✨
- Use Maven plugin for automatic formatting 🛠️

### 📝 Commit Specifications
- feat: New feature ✨
- fix: Fix bug 🐛
- docs: Documentation change 📚
- style: Code format 🎨
- refactor: Refactor 🔄
- test: Test 🧪
- chore: Build or auxiliary tool change 🛠️

## 🤝 Contribution Guide

1. Fork the project 🍴
2. Create a feature branch (`git checkout -b feature/AmazingFeature`) 🌿
3. Commit changes (`git commit -m 'Add some AmazingFeature'`) 💬
4. Push to branch (`git push origin feature/AmazingFeature`) 📤
5. Open a Pull Request 🚀

## 📄 License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details.

## 📞 Contact Information

- **Project Home**: https://github.com/rpamis/rpamis 🏠
- **Issue Tracking**: https://github.com/rpamis/rpamis/issues 🐛
- **Development Email**: benyuanming@gmail.com ✉️

## 🙏 Acknowledgments

Thank you to all developers who have contributed to RPAMIS!

---

**RPAMIS** - Make development simpler and more efficient! 🚀