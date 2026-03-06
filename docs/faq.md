# Rpamis 常见问题

## 入门问题

### Q: Rpamis 是什么？
A: Rpamis（Rapid Project Architecture and Micro Infrastructure Service）是一个开源的 Java 基础设施库，为开发者提供快速、统一的项目结构生成，统一的包管理工具，并提供开箱即用的开发效能工具。

### Q: Rpamis 需要什么版本的 Java？
A: Rpamis 要求 Java 17 及以上版本。

### Q: Rpamis 支持哪些框架？
A: Rpamis 主要支持 Spring Boot 3.x 框架，但也可以在其他 Java 项目中使用。

## 依赖管理

### Q: 如何引入 Rpamis 依赖？
A: 在 Maven 项目中，您可以直接在 `pom.xml` 中引入所需的模块依赖：

```xml
<dependency>
    <groupId>com.rpamis</groupId>
    <artifactId>rpamis-exception-spring-boot-starter</artifactId>
    <version>1.0.2</version>
</dependency>
```

### Q: 所有模块都需要引入吗？
A: 不需要，您可以根据需要引入所需的模块。核心依赖模块通常是必须的，但 Starters 模块是可选的。

### Q: 如何查看最新版本？
A: 您可以在 [Maven Central](https://search.maven.org/search?q=g:com.rpamis) 上查看最新版本。

## 统一响应模块

### Q: 如何自定义响应格式？
A: 您可以继承 `Response` 类并重写相应方法，或者创建自己的响应类。

### Q: Response 类的泛型类型是什么？
A: Response 类的泛型类型 `T` 是响应数据的类型，可以是任何 Java 类型。

### Q: 为什么我的响应没有返回 data 字段？
A: 如果您使用 `Response.success()` 而不带参数，data 字段将为 `null`。

## 异常处理

### Q: 什么时候应该使用 BizException 而不是 SysException？
A:
- **BizException**：用于业务逻辑异常，不会打印堆栈信息（提高性能）
- **SysException**：用于系统级异常，会打印完整堆栈信息

### Q: 异常处理会影响性能吗？
A: 不会显著影响，尤其是 BizException 已优化为无堆栈信息的版本。

## 可缓存枚举

### Q: 枚举类需要满足什么条件才能被缓存？
A: 枚举类需要实现 `CachableEnum` 接口，定义 `getCode()` 和 `getDesc()` 方法。

### Q: 如何自定义枚举扫描路径？
A: 在 `application.yml` 中配置：

```yaml
rpamis:
  enum:
    scan-packages: com.example.enums
```

### Q: 枚举缓存是线程安全的吗？
A: 是的，枚举缓存使用 `ConcurrentHashMap` 实现，是线程安全的。

## 分布式追踪

### Q: 如何启用 SkyWalking 追踪？
A: 在 `application.yml` 中配置：

```yaml
rpamis:
  trace:
    type: skywalking
```

### Q: TraceFilter 需要手动配置吗？
A: 不需要，当引入 `rpamis-exception-spring-boot-starter` 时，会自动配置。

### Q: 如何禁用请求响应日志？
A: 在 `application.yml` 中配置：

```yaml
rpamis:
  trace:
    enabled: false
```

## SPI 扩展机制

### Q: 如何创建 SPI 扩展？
A:
1. 定义带 `@RpamisSpi` 注解的接口
2. 实现接口
3. 在 `META-INF/rpamis/` 目录下创建配置文件

### Q: SPI 配置文件的命名规则是什么？
A: 配置文件的文件名必须与接口的全限定名相同。

### Q: 如何加载 SPI 实现？
A: 使用 `SpiLoader.getLoader(Class)` 方法加载。

## 架构生成工具

### Q: 如何使用架构生成工具？
A: 可以通过 API 接口直接调用，或者使用命令行工具。

### Q: 支持哪些项目模板？
A: 目前支持单模块、多模块、Web、API、Starter 等项目模板。

### Q: 可以自定义项目模板吗？
A: 可以，通过扩展项目模板管理类实现。

## 部署与发布

### Q: 如何构建项目？
A: 使用 Maven 命令：

```bash
mvn clean install
```

### Q: 如何发布到 Maven Central？
A: 使用 Sonatype OSSRH 发布流程，配置相应的 profile。

### Q: 如何格式化代码？
A: 使用 Maven 插件：

```bash
mvn spring-javaformat:apply
```

## 性能优化

### Q: 如何提高枚举查找的性能？
A: 使用 EnumLookup 而不是普通的枚举查找方法，它的时间复杂度是 O(1)。

### Q: 如何优化异常处理的性能？
A: 使用 BizException 而不是 SysException，BizException 会跳过堆栈信息收集。

### Q: 如何减少响应对象的内存消耗？
A: 使用不带详细信息的响应方法，或者设置 details 为 null。

## 调试与日志

### Q: 如何查看详细的日志信息？
A: 在 `application.yml` 中配置：

```yaml
logging:
  level:
    com.rpamis: DEBUG
```

### Q: 如何自定义追踪日志的格式？
A: 继承 TraceDecorator 类并重写相应方法。

### Q: 如何启用 MDC 日志跟踪？
A: 在使用 SLF4J 时，会自动启用 MDC 跟踪。

## 问题排查

### Q: 为什么 EnumLookup 找不到我的枚举？
A:
1. 检查枚举类是否实现了 CachableEnum 接口
2. 检查扫描路径配置是否正确
3. 确保枚举类在 classpath 中

### Q: 为什么异常处理没有生效？
A:
1. 检查是否引入了 `rpamis-exception-spring-boot-starter` 依赖
2. 检查配置是否正确
3. 确保异常被正确抛出

### Q: 为什么请求响应日志没有记录？
A:
1. 检查是否引入了正确的依赖
2. 检查是否禁用了追踪功能
3. 检查日志级别配置是否正确