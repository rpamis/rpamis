# Rpamis 🚀

[![Maven Central](https://img.shields.io/maven-central/v/com.rpamis/rpamis.svg?style=flat-square)](https://search.maven.org/search?q=g:com.rpamis)
[![GitHub Actions](https://github.com/rpamis/rpamis/workflows/full-build/badge.svg?branch=master)](https://github.com/rpamis/rpamis/actions)
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg?style=flat-square)](https://www.apache.org/licenses/LICENSE-2.0.html)
[![Java Version](https://img.shields.io/badge/Java-17+-blue.svg?style=flat-square)](https://adoptium.net/)

## 📖 Introduction

**R**apid **P**roject **A**rchitecture and **M**icro **I**nfrastructure **S**ervice(RPAMIS) - Rapid Project Architecture and Micro Infrastructure Service.

Provides developers with fast, unified project structure generation, unified package management tools, and ready-to-use development efficiency tools.

## ✨ Core Features

### 🚀 Project Scaffolding
- **rpamis-architecture-build** - Quickly generate Spring Boot multi-module Maven projects
- Supports single-module and multi-module project structure generation
- Automatically configures project dependencies and architecture
- Provides complete development and build scripts

### 🛡️ Exception Handling System
- **rpamis-exception-spring-boot-starter** - Exception Starter for automatic configuration of exception handling
- **rpamis-common-exception** - Core exception handling module
  - Provides custom validators
  - Supports various types of exception handling (business exceptions, system exceptions, validation exceptions, etc.)
  - Global exception handling
  - Dubbo exception handling Filter
  - Unified exception response format

### 📊 Enum Cache Mechanism
- **rpamis-enum-core** - Implements enum caching functionality
- **CachableEnum Interface** - Unified enum definition approach
- **EnumLookup SDK** - Built-in enum lookup tool, directly obtain Value from Key
- Eliminates the need to repeatedly write code to retrieve enum values within enum classes

### 🔌 SPI Extension Mechanism
- **rpamis-extension-spi** - SPI plugin package provided by RPAMIS
- **@RpamisSpi Annotation** - Used to mark SPI interfaces
- **SPI Configuration Files** - Create configuration files in `resource/META-INFO/rpamis` directory, format: `interfaceName=com.xxx.ImplementationClass` (e.g., `customStrategy=com.xxx.CustomStrategy`)
- **Support for Multiple Injection Methods** - SPI implementation classes support pure Java and Spring Bean injection
- **Replace System Core Content** - Facilitates users to use SPI mode to replace core content in their systems

### 🎯 Other Core Features
- 🔍 **Distributed Tracing** - Automatically records request and response logs, supporting distributed tracing
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

### 📊 Enum Usage Example

```java
import com.rpamis.enumcore.common.CachableEnum;
import com.rpamis.enumcore.EnumLookup;

// 1. Define enum class and implement CachableEnum interface
public enum OrderStatusEnum implements CachableEnum<Integer, String> {
    PENDING(1, "Pending"),
    PAID(2, "Paid"),
    SHIPPED(3, "Shipped"),
    DELIVERED(4, "Delivered"),
    CANCELLED(5, "Cancelled");

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

// 2. Use EnumLookup directly in code to get enum values
public class OrderService {

    public String getOrderStatusDesc(Integer status) {
        // Directly retrieve enum values using EnumLookup, no need to repeatedly write code in enum classes
        OrderStatusEnum statusEnum = EnumLookup.findEnumByCode(OrderStatusEnum.class, status);
        return statusEnum != null ? statusEnum.getDesc() : "Unknown Status";
    }
}
```

### 🔌 SPI Usage Example

```java
import com.rpamis.extension.spi.RpamisSpi;
import org.springframework.stereotype.Component;

// 1. Define SPI interface and use @RpamisSpi annotation
@RpamisSpi
public interface CustomStrategy {
    String execute(String param);
}

// 2. Implement SPI interface (supports pure Java or Spring Bean injection)
@Component // If using Spring Bean injection, add this annotation
public class DefaultStrategy implements CustomStrategy {
    @Override
    public String execute(String param) {
        return "Default strategy: " + param;
    }
}

// 3. Create configuration file in resource/META-INFO/rpamis directory
// File name: com.example.CustomStrategy
// File content:
// default=com.example.DefaultStrategy

// 4. Use SPI in code
import com.rpamis.extension.spi.SpiLoader;

public class StrategyClient {
    public static void main(String[] args) {
        CustomStrategy strategy = SpiLoader.getLoader(CustomStrategy.class).getExtension("default");
        String result = strategy.execute("test parameter");
        System.out.println(result); // Output: Default strategy: test parameter
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
| **rpamis-common-exception** | Common exception handling mechanism (BizException, SysException, etc.), providing custom validators, various types of exception handling, global exception handling, and Dubbo exception handling Filter | Core Library |
| **rpamis-common-trace** | Distributed tracing and logging tools | Core Library |
| **rpamis-common-trace-toolkit** | Tracing toolkit (supports SkyWalking) | Core Library |
| **rpamis-common-utils** | Common utility class collection (Snowflake ID, Bean tools, JSON tools, etc.) | Core Library |

### ⚡ Spring Boot Starters
| Module Name | Main Function | Type |
|------------|--------------|------|
| **rpamis-exception-spring-boot-starter** | Exception Starter, automatically configures exception handling, core dependency on rpamis-common-exception | Starter |
| **rpamis-enum-spring-boot-starter** | Cacheable enum automatic configuration | Starter |

### 🔧 Architecture and Extension Modules
| Module Name | Main Function | Type |
|------------|--------------|------|
| **rpamis-architecture-build** | Project scaffolding for quickly generating Spring Boot multi-module Maven projects | Architecture Tool |
| **rpamis-enum-core** | Implements enum caching functionality, providing CachableEnum interface and EnumLookup SDK for directly retrieving enum values by key | Core Library |
| **rpamis-exception-dto** | Exception data transfer objects | Core Library |
| **rpamis-extension-spi** | SPI plugin package provided by RPAMIS. Supports users to annotate interfaces with @RpamisSpi, implement the corresponding interfaces, and configure them in the resource/META-INFO/rpamis directory. Supports pure Java and Spring Bean injection | Extension Library |
| **rpamis-extension-aspect** | Extension aspect support | Extension Library |

## 📚 Detailed Documentation

- 📖 [Usage Guide](docs/usage.md) - Detailed function usage instructions
- 📚 [API Documentation](https://rpamis.github.io/rpamis/) - Complete API documentation
- 📦 [Module Descriptions](docs/modules.md) - Detailed function descriptions for each module
- ❓ [FAQ](docs/faq.md) - Frequently Asked Questions

## 🚀 Rapid Architecture Generation

### Using Project Scaffolding

rpamis-architecture-build is a powerful project scaffolding tool for quickly generating Spring Boot multi-module Maven projects.

#### 1. Add Dependency

```xml
<dependency>
    <groupId>com.rpamis</groupId>
    <artifactId>rpamis-architecture-build</artifactId>
    <version>1.0.2</version>
</dependency>
```

#### 2. Generate Project Using API

```java
import com.rpamis.architecture.build.ArchitectureBuildController;
import com.rpamis.architecture.build.vo.BaseProjectConfig;

public class ProjectGenerator {

    public static void main(String[] args) {
        // Configure project basic information
        BaseProjectConfig config = new BaseProjectConfig();
        config.setGroupId("com.example");
        config.setArtifactId("my-project");
        config.setVersion("1.0.0");
        config.setPackageName("com.example.myproject");
        config.setDescription("My Example Project");

        // Generate multi-module project
        ArchitectureBuildController controller = new ArchitectureBuildController();
        controller.buildMultiModuleProject(config);

        System.out.println("Project generated successfully!");
    }
}
```

#### 3. Supported Project Types

- **Single-module project** - Suitable for rapid development of small projects
- **Multi-module project** - Suitable for large project architectures, including business modules, base modules, API modules, etc.
- **Spring Boot Starter** - Quickly create custom Spring Boot Starter projects

#### 4. Project Structure Example

The generated project structure is as follows:

```
my-project/
├── my-project-common/        # Common base module
├── my-project-dao/          # Data access module
├── my-project-service/      # Business logic module
├── my-project-api/          # API interface module
├── my-project-web/          # Web application module
├── my-project-starter/      # Custom Starter
└── pom.xml                  # Parent project dependency management
```

#### 5. Automatic Configuration

The project scaffolding automatically configures:

- Unified dependency management
- Code formatting tools
- Test framework configuration
- CI/CD configuration
- Common development tool integration

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