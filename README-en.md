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

For detailed usage, please refer to: [Exception Handling Usage Guide](docs/exception-handling/usage.md)

### ⚠️ Exception Handling Example

For detailed usage, please refer to: [Exception Handling Usage Guide](docs/exception-handling/usage.md)

### 📊 Enum Usage Example

For detailed usage, please refer to: [Enum Cache Mechanism Usage Guide](docs/enum-cache/usage.md)

### 🔌 SPI Usage Example

For detailed usage, please refer to: [SPI Extension Mechanism Usage Guide](docs/spi-extension/usage.md)

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

For detailed usage, please refer to: [Project Scaffolding Usage Guide](docs/project-scaffolding/usage.md)

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