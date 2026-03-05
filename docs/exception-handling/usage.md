# 异常处理使用指南 (rpamis-exception-spring-boot-starter)

## 概述

rpamis-exception-spring-boot-starter 是 RPAMIS 提供的异常处理模块，它的核心模块是 rpamis-common-exception，提供了自定义的校验器、多种类型异常处理、全局异常处理以及 Dubbo 异常处理 Filter。

## 功能特性

- **自定义校验器**：提供灵活的参数校验机制
- **多种类型异常处理**：支持业务异常、系统异常、验证异常等
- **全局异常处理**：统一的异常处理机制
- **Dubbo 异常处理 Filter**：在 Dubbo 服务调用中的异常处理
- **统一的异常响应格式**：标准化的异常响应结构

## 快速开始

### 1. 引入依赖

```xml
<dependency>
    <groupId>com.rpamis</groupId>
    <artifactId>rpamis-exception-spring-boot-starter</artifactId>
    <version>1.0.2</version>
</dependency>
```

### 2. 异常类型

rpamis-common-exception 提供了多种异常类型，包括：

| 异常类型 | 描述 |
| --- | --- |
| BizException | 业务异常，用于表示业务逻辑错误 |
| SysException | 系统异常，用于表示系统级错误 |
| ValidException | 验证异常，用于表示参数验证失败 |
| CodedException | 带编码的异常，用于需要返回特定错误码的场景 |

### 3. 异常使用示例

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

## 全局异常处理

### 1. 全局异常处理配置

rpamis-exception-spring-boot-starter 会自动配置全局异常处理，但您也可以自定义：

```java
import com.rpamis.common.exception.handler.ExceptionHandler;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExceptionHandlerConfig {

    @Bean
    public ExceptionHandler customExceptionHandler() {
        return new ExceptionHandler() {
            @Override
            public Object handleException(Exception e) {
                // 自定义异常处理逻辑
                return Response.fail("CUSTOM_ERROR", e.getMessage());
            }
        };
    }
}
```

### 2. 异常响应格式

所有异常都会被转换为统一的响应格式：

```json
{
    "code": "ERROR_CODE",
    "message": "错误信息",
    "details": "详细信息",
    "data": null
}
```

## 自定义校验器

### 1. 创建自定义校验器

```java
import com.rpamis.common.exception.validator.Validator;

public class CustomValidator implements Validator<Object> {
    @Override
    public boolean validate(Object value) {
        // 自定义验证逻辑
        return value != null && value.toString().length() > 0;
    }

    @Override
    public String getErrorMessage() {
        return "值不能为空";
    }
}
```

### 2. 使用自定义校验器

```java
import com.rpamis.common.exception.annotation.Validate;
import com.rpamis.common.exception.validator.Validator;

public class UserDTO {

    @Validate(validator = CustomValidator.class)
    private String username;

    // 其他字段和方法
}
```

## Dubbo 异常处理

### 1. Dubbo 异常处理 Filter

rpamis-common-exception 提供了 Dubbo 异常处理 Filter，可以自动处理 Dubbo 服务调用中的异常：

```xml
<dubbo:provider filter="exceptionFilter" />
<dubbo:consumer filter="exceptionFilter" />
```

### 2. 异常转换

在 Dubbo 服务调用中，异常会被转换为统一的格式，确保客户端和服务端都能正确解析。

## 配置选项

### 1. 异常处理配置

您可以在 `application.properties` 或 `application.yml` 中配置异常处理选项：

```yaml
rpamis:
  exception:
    enabled: true               # 是否启用异常处理
    printStack: false           # 是否打印堆栈信息
    defaultCode: "ERROR"        # 默认错误码
    defaultMessage: "系统错误"  # 默认错误信息
```

### 2. 验证配置

```yaml
rpamis:
  validation:
    enabled: true               # 是否启用参数验证
    failFast: true              # 是否快速失败
```

## 常见问题

### 1. 如何自定义异常响应格式？

您可以实现自定义的 ExceptionHandler 来覆盖默认的异常响应格式。

### 2. 如何添加自定义异常类型？

您可以继承 CodedException 或其他基础异常类来创建自定义异常类型。

### 3. 参数验证不生效怎么办？

确保您已正确配置验证器，并在需要验证的字段上添加了 @Validate 注解。

## 参考链接

- [官方文档](https://github.com/rpamis/rpamis/wiki/Exception-Handling)
- [API 文档](https://rpamis.github.io/rpamis)