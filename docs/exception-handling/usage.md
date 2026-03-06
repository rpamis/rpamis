# 异常处理使用指南 (rpamis-exception-spring-boot-starter)

## 概述

rpamis-exception-spring-boot-starter 是 RPAMIS 提供的异常处理模块，它的核心模块是 rpamis-common-exception 和 rpamis-exception-dto，提供了自定义的校验器、多种类型异常处理、全局异常处理以及 Dubbo 异常处理 Filter。

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
| BizNoStackException | 无堆栈的业务异常，用于不需要打印堆栈的场景 |
| SysException | 系统异常，用于表示系统级错误 |
| ValidException | 验证异常，用于表示参数验证失败 |
| RpamisException | 自定义异常，接受任意状态码 |

### 3. 异常使用示例

```java
import com.rpamis.exception.dto.BizException;
import com.rpamis.exception.dto.ExceptionFactory;

@Service
public class DemoService {

    public void doBusiness(boolean someCondition) {
        try {
            // 业务逻辑
            if (someCondition) {
                throw ExceptionFactory.bizException("业务操作失败", new RuntimeException("详细信息"));
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
    "errCode": "ERROR_CODE",
    "errMessage": "错误信息",
    "detailMessage": "详细信息",
    "data": null
}
```

### 3. 异常响应Http Code
- 日志级别WARN:对于前置校验类异常，正常来说状态码为400，代表前端参数错误，400状态下前端不能直接拿到返回体，需要前端异常捕获配合才能打印msg，该类型异常已知，不需要人工处理
- 日志级别WARN:对于业务类校验异常ValidException(不带堆栈)，状态码为200，表示请求正常只是业务拦截，该类型异常已知，不需要人工处理
- 日志级别WARN：对于业务类异常BizException(带堆栈)、BizNoStackException(不带堆栈)，状态码200，表示请求正常只是业务拦截，该类型异常已知，不需要人工处理
- 日志级别ERROR:对于已知可能发生的系统级异常SysException(带堆栈)，状态码为500，表示出现系统异常，开发者手动抛出该异常说明，该系统级异常已知，需要人工处理
- 日志级别ERROR:对于未知的发生的系统级异常Exception(带堆栈)，状态码500，表示出现未知的没有被try catch的异常，需要人工处理
- 日志级别WARN:用于非固定状态码任意位置的异常RpamisException(可带堆栈、也可不带)，状态码200，由于该类接受任意状态码，目的是兼容前端对接业务状态码场景，可用于兼容老项目做全局异常
- 强调http code规范，弱化业务code属性，业务code属性理论上属于后端开发需要观测，前端仅需根据http code做出对应处理

## 自定义校验器

```java
import com.rpamis.common.exception.annotation.Validate;
import com.rpamis.common.exception.validator.Validator;

public class UserDTO {

    @SpecifiesValidator(message = "status必须符合枚举", enumClass = StatusEnum.class)
    private String status;

    // 其他字段和方法
}
```

## Dubbo 异常处理

### 1. Dubbo 异常处理 Filter

rpamis-common-exception 提供了 Dubbo 异常处理 Filter，可以自动处理 Dubbo 服务调用中的异常：

开启方式在`resource/META-INF/dubbo/org.apache.dubbo.rpc.Filter`中添加：

```
DubboExceptionFilter=com.rpamis.common.exception.exception.DubboExceptionFilter
```

### 2. 异常转换

在 Dubbo 服务调用中，异常会被转换为统一的格式，确保客户端和服务端都能正确解析。

## 配置选项

### 1. 异常处理配置

您可以在 `application.properties` 或 `application.yml` 中配置异常处理选项：

```yaml
rpamis:
  exception:
    # 是否开启全局Web异常
    enabled: true
    # 是否开启全局RPC统一返回体
    rpc-pack: true
    # 是否开启Exception.class捕获
    include-exception-class: true
```
