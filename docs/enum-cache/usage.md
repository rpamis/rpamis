# 枚举缓存使用指南 (rpamis-enum-core)

## 概述

rpamis-enum-core 实现了枚举缓存功能，提供了 CachableEnum 接口，可以通过内置 SDK EnumLookup 直接获取某个枚举的 Key 对应 Value，避免反复在枚举中写重复获取的代码。

## 功能特性

- **枚举缓存**：自动缓存所有实现了 CachableEnum 接口的枚举类
- **高性能查询**：提供 O(1) 时间复杂度的枚举查找
- **统一接口**：通过 CachableEnum 接口定义统一的枚举规范
- **内置 SDK**：提供 EnumLookup 工具类简化枚举查询
- **线程安全**：使用线程安全的 Map 实现缓存

## 快速开始

### 1. 引入依赖

```xml
<dependency>
    <groupId>com.rpamis</groupId>
    <artifactId>rpamis-enum-core</artifactId>
    <version>1.0.2</version>
</dependency>

<!-- 如果使用 Spring Boot，可以直接引入 Starter -->
<dependency>
    <groupId>com.rpamis</groupId>
    <artifactId>rpamis-enum-spring-boot-starter</artifactId>
    <version>1.0.2</version>
</dependency>
```

### 2. 定义枚举类

```java
import com.rpamis.enums.core.CachableEnum;

// 1. 定义枚举类并实现 CachableEnum 接口
public enum OrderStatusEnum implements CachableEnum<Integer, String> {
    PENDING(1, "待支付"),
    PAID(2, "已支付"),
    SHIPPED(3, "已发货"),
    DELIVERED(4, "已收货"),
    CANCELLED(5, "已取消");

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
```

### 3. 在代码中使用 EnumLookup 直接获取枚举值

```java
import com.rpamis.enums.core.EnumLookup;

public class OrderService {

    public String getOrderStatusDesc(Integer status) {
        // 直接通过 EnumLookup 获取枚举值，无需在枚举中反复编写获取代码
        OrderStatusEnum statusEnum = EnumLookup.getEnumByCode(OrderStatusEnum.class, status);
        return statusEnum != null ? statusEnum.getDesc() : "未知状态";
    }
}
```

## 高级用法

### 1. 根据描述获取枚举值

```java
import com.rpamis.enums.core.EnumLookup;

public class EnumDemo {
    public static void main(String[] args) {
        // 根据描述获取枚举值
        OrderStatusEnum status = EnumLookup.getEnumByDesc(OrderStatusEnum.class, "已支付");

        if (status != null) {
            System.out.println("Found: Code = " + status.getCode() + ", Desc = " + status.getDesc());
        } else {
            System.out.println("Not found");
        }
    }
}
```

## 常见问题

### 1. EnumLookup 找不到枚举值怎么办？

- 确保枚举类正确实现了 CachableEnum 接口
- 检查传入的 Key 是否在枚举类定义中存在
- 确保枚举类已经被正确地加载到 JVM 中

### 2. 性能如何？

- 枚举查找操作的时间复杂度为 O(1)
- 缓存是线程安全的
- 枚举值只会被加载一次，之后会被缓存起来

### 3. 是否支持继承？

CachableEnum 是一个接口，枚举类可以继承其他接口，但不能继承类。

### 4. 如何处理复杂的枚举结构？

对于复杂的枚举结构，您可以考虑使用多个枚举类，或者在枚举类中添加额外的字段和方法。

## 最佳实践

1. **统一枚举规范**：所有枚举类都应该实现 CachableEnum 接口
2. **避免重复代码**：使用 EnumLookup 来替代在每个枚举类中编写重复的获取代码
3. **考虑线程安全**：虽然枚举类本身是线程安全的，但如果您需要自定义操作，应该考虑线程安全问题
4. **合理使用缓存**：对于大型项目，可以考虑禁用不必要的枚举类的缓存