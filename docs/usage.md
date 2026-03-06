# Rpamis 使用指南

本文档详细介绍 Rpamis 各个模块的使用方法和最佳实践。

## 统一响应模块 (rpamis-common-dto)

### Response 类使用

#### 1. 成功响应

```java
// 无数据成功响应
return Response.success();

// 带数据成功响应
return Response.success(data);
```

#### 2. 失败响应

```java
// 基本失败响应
return Response.fail("ERROR_CODE", "错误信息");

// 带详细信息的失败响应
return Response.fail("ERROR_CODE", "错误信息", "详细错误描述");
```

#### 3. 响应属性说明

| 属性名 | 类型 | 描述 |
|-------|------|------|
| success | boolean | 响应是否成功 |
| code | String | 响应码 |
| message | String | 响应信息 |
| details | String | 详细信息 |
| data | T | 响应数据 |

## 异常处理模块 (rpamis-common-exception)

### 异常类型

#### 1. 业务异常 (BizException)

用于处理业务逻辑异常，不会打印堆栈信息（提高性能）。

```java
throw new BizException("业务操作失败");

throw new BizException("业务操作失败", "详细信息");
```

#### 2. 系统异常 (SysException)

用于处理系统级异常，会打印完整堆栈信息。

```java
throw new SysException("系统异常", e);
```

#### 3. 验证异常 (ValidException)

用于数据验证失败的场景。

```java
throw new ValidException("参数验证失败", errors);
```

### 异常工厂

使用 ExceptionFactory 统一创建异常实例：

```java
// 创建业务异常
throw ExceptionFactory.bizException("业务操作失败");

// 创建系统异常
throw ExceptionFactory.sysException("系统异常", e);

// 创建验证异常
throw ExceptionFactory.validException("参数验证失败", errors);
```

## 可缓存枚举模块 (rpamis-enum-core)

### 基本使用

#### 1. 定义枚举

```java
import com.rpamis.enumcore.common.CachableEnum;

public enum StatusEnum implements CachableEnum<Integer, String> {

    ACTIVE(1, "激活"),
    INACTIVE(0, "禁用");

    private final Integer code;
    private final String desc;

    StatusEnum(Integer code, String desc) {
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
```

#### 2. 查询枚举

```java
// 根据 Code 查询枚举
StatusEnum status = EnumLookup.findEnumByCode(StatusEnum.class, 1);

// 根据 Desc 查询枚举
StatusEnum status = EnumLookup.findEnumByDesc(StatusEnum.class, "激活");

// 获取所有枚举
List<StatusEnum> allStatus = EnumLookup.getAllEnums(StatusEnum.class);
```

### 自动配置 (rpamis-enum-spring-boot-starter)

在 Spring Boot 项目中，只需引入依赖：

```xml
<dependency>
    <groupId>com.rpamis</groupId>
    <artifactId>rpamis-enum-spring-boot-starter</artifactId>
    <version>1.0.2</version>
</dependency>
```

枚举会自动扫描并缓存，支持通过注解配置扫描路径。

## 分布式追踪模块 (rpamis-common-trace)

### 1. 追踪过滤器配置

```java
@Configuration
public class TraceConfig {

    @Bean
    public TraceFilter traceFilter() {
        return new TraceFilter();
    }

    @Bean
    public FilterRegistrationBean<TraceFilter> traceFilterRegistration() {
        FilterRegistrationBean<TraceFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(traceFilter());
        registration.addUrlPatterns("/*");
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registration;
    }
}
```

### 2. 追踪类型配置

```yaml
rpamis:
  trace:
    type: skywalking # 可选值: rpamis (默认), skywalking
```

### 3. 自定义追踪日志

```java
import com.rpamis.common.trace.util.TraceLogUtil;

public class MyService {

    public void doBusiness() {
        TraceLogUtil.info("业务操作开始");
        try {
            // 业务逻辑
            TraceLogUtil.debug("中间步骤执行成功");
        } catch (Exception e) {
            TraceLogUtil.error("业务操作失败", e);
        }
    }
}
```

## SPI 扩展机制 (rpamis-extension-spi)

### 1. 定义 SPI 接口

```java
import com.rpamis.extension.spi.RpamisSpi;

@RpamisSpi
public interface MyExtension {

    void doSomething();
}
```

### 2. 实现 SPI 接口

```java
public class MyExtensionImpl implements MyExtension {

    @Override
    public void doSomething() {
        System.out.println("扩展实现");
    }
}
```

### 3. 创建 SPI 配置文件

在 `META-INF/rpamis/` 目录下创建文件名与接口全限定名相同的文件，内容为实现类的全限定名：

```
com.example.MyExtensionImpl
```

### 4. 加载 SPI 实现

```java
import com.rpamis.extension.spi.SpiLoader;

public class MyService {

    public void useExtension() {
        MyExtension extension = SpiLoader.getLoader(MyExtension.class).getExtension();
        extension.doSomething();
    }
}
```

## 最佳实践

### 1. 响应设计最佳实践

```java
@RestController
@RequestMapping("/api")
public class UserController {

    @GetMapping("/users/{id}")
    public Response<UserDTO> getUser(@PathVariable Long id) {
        try {
            UserDTO user = userService.getUserById(id);
            if (user == null) {
                return Response.fail("USER_NOT_FOUND", "用户不存在");
            }
            return Response.success(user);
        } catch (BizException e) {
            return Response.fail("BUSINESS_ERROR", e.getMessage());
        } catch (Exception e) {
            return Response.fail("SYSTEM_ERROR", "系统异常，请稍后重试");
        }
    }
}
```

### 2. 异常处理最佳实践

```java
@Service
public class UserService {

    public UserDTO getUserById(Long id) {
        if (id <= 0) {
            throw ExceptionFactory.validException("用户ID必须大于0", null);
        }

        User user = userRepository.findById(id);
        if (user == null) {
            throw ExceptionFactory.bizException("用户不存在");
        }

        return convertToDto(user);
    }
}
```

### 3. 枚举使用最佳实践

```java
@Service
public class OrderService {

    public String getOrderStatusDesc(Integer status) {
        OrderStatusEnum statusEnum = EnumLookup.findEnumByCode(OrderStatusEnum.class, status);
        return statusEnum != null ? statusEnum.getDesc() : "未知状态";
    }

    public List<Map<String, Object>> getStatusOptions() {
        return EnumLookup.getAllEnums(OrderStatusEnum.class).stream()
            .map(e -> {
                Map<String, Object> map = new HashMap<>();
                map.put("code", e.getCode());
                map.put("desc", e.getDesc());
                return map;
            })
            .collect(Collectors.toList());
    }
}
```