# SPI扩展机制使用指南 (rpamis-extension-spi)

## 概述

rpamis-extension-spi 是 rpamis 提供的 SPI（Service Provider Interface）插件包，支持用户用 @RpamisSpi 标注接口，然后实现对应的接口，用户可以创建 META-INF/rpamis 目录，然后将对应的 SPI 接口写到文件中，如 customStrategy=com.xxx.CustomStrategy，SPI 实现类中支持纯 Java 和 Spring Bean 的注入，方便用户使用 SPI 模式替代自己系统中的核心内容。

## 功能特性

- **@RpamisSpi 注解**：用于标注 SPI 接口
- **SPI 配置文件**：在 META-INF/rpamis 目录中创建配置文件
- **支持多种注入方式**：SPI 实现类中支持纯 Java 和 Spring Bean 的注入
- **灵活的扩展机制**：方便用户使用 SPI 模式替代自己系统中的核心内容
- **自动加载机制**：自动扫描并加载 SPI 实现类

## 快速开始

### 1. 引入依赖

```xml
<dependency>
    <groupId>com.rpamis</groupId>
    <artifactId>rpamis-extension-spi</artifactId>
    <version>1.0.2</version>
</dependency>
```

### 2. 定义 SPI 接口

```java
import com.rpamis.extension.spi.RpamisSpi;

// 1. 定义 SPI 接口并使用 @RpamisSpi 注解
@RpamisSpi
public interface CustomStrategy {
    String execute(String param);
}
```

### 3. 实现 SPI 接口

```java
import org.springframework.stereotype.Component;

// 2. 实现 SPI 接口（支持纯 Java 或 Spring Bean 注入）
@Component // 如果是 Spring Bean 注入，需要添加此注解
public class DefaultStrategy implements CustomStrategy {
    @Override
    public String execute(String param) {
        return "Default strategy: " + param;
    }
}

// 另一个实现类
@Component
public class AdvancedStrategy implements CustomStrategy {
    @Override
    public String execute(String param) {
        return "Advanced strategy: " + param.toUpperCase();
    }
}
```

### 4. 创建配置文件

在 `META-INF/rpamis` 目录下创建配置文件，文件名称应该是 SPI 接口的全限定名（包括包名），内容格式为：`实现名称=实现类全限定名`。

例如，对于 `com.example.CustomStrategy` 接口，创建名为 `com.example.CustomStrategy` 的文件，内容如下：

```properties
default=com.example.DefaultStrategy
advanced=com.example.AdvancedStrategy
```

### 5. 在代码中使用 SPI

```java
import com.rpamis.extension.spi.SpiLoader;

public class StrategyClient {
    public static void main(String[] args) {
        // 获取 SPI 加载器
        CustomStrategy defaultStrategy = SpiLoader.getSpiLoader(CustomStrategy.class).getSpiImpl("default");
        String result1 = defaultStrategy.execute("test parameter");
        System.out.println(result1); // 输出：Default strategy: test parameter

        // 获取另一个扩展
        CustomStrategy advancedStrategy = SpiLoader.getSpiLoader(CustomStrategy.class).getSpiImpl("advanced");
        String result2 = advancedStrategy.execute("test parameter");
        System.out.println(result2); // 输出：Advanced strategy: TEST PARAMETER
    }
}
```

## 高级用法

### 1. 使用默认扩展

如果您希望在没有指定扩展名称的情况下使用默认扩展，可以通过以下方式：

```java
@RpamisSpi("default")
public interface CustomStrategy {
    String execute(String param);
}

// 在配置文件中
default=com.example.DefaultStrategy
```

### 2. 动态加载扩展

```java
import com.rpamis.extension.spi.SpiLoader;

public class DynamicExtensionLoader {
    public static void main(String[] args) {
        // 动态加载扩展
        CustomStrategy strategy = SpiLoader.getSpiLoader(CustomStrategy.class).getSpiImpl("advanced");

        // 获取所有可用的扩展名称
        Set<String> supportedExtensions = SpiLoader.getSpiLoader(CustomStrategy.class).getSupportedSpiImpl();

        System.out.println("Supported extensions: " + supportedExtensions); // 输出：[default, advanced]
    }
}
```

### 3. 扩展类之间的依赖注入

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdvancedStrategy implements CustomStrategy {

    @Autowired
    private SomeService someService;

    @Override
    public String execute(String param) {
        return "Advanced strategy with dependency injection: " + someService.process(param);
    }
}
```

### 4. 使用 Spring 环境

```java
import com.rpamis.extension.spi.SpiLoader;

public class SpringAwareClient {
    @Autowired
    private CustomStrategy customStrategy;

    public String doSomething(String param) {
        return customStrategy.execute(param);
    }
}
```

## 最佳实践

1. **明确接口定义**：SPI 接口应该有明确的功能定义
2. **提供默认实现**：为 SPI 接口提供默认实现，以便用户可以快速上手
3. **文档化接口**：详细文档化 SPI 接口的功能、使用方法和配置选项
4. **向后兼容**：在更改 SPI 接口时，确保向后兼容
5. **使用 Maven 模块**：将 SPI 接口和实现分离到不同的 Maven 模块中