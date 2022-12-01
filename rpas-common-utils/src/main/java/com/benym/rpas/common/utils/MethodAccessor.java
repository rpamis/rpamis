package com.benym.rpas.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;

/**
 * 为什么使用MethodHandle
 * MethodHandle性能压测如何
 * @link {<a href="https://www.optaplanner.org/blog/2018/01/09/JavaReflectionButMuchFaster.html">...</a>}
 * <p/>
 * 由于在类外进行私有变量的访问
 * 在JDK8环境下
 * MethodHandle虽然单独有效，但和LambdaMetafactory.metafactory BiConsumer很难结合在一起
 * @link {<a href="https://stackoverflow.com/questions/28184065/java-8-access-private-member-with-lambda">...</a>}
 * <p/>
 * 在JDK9环境下MethodHandle提供privateLookupIn则才能和LambdaMetafactory.metafactory结合
 * <p/>
 * tip: 即使不采用LambdaMetafactory方式的ASM增强，MethodHandle仍然是通过底层native执行方法比反射方法快很多
 * <p/>
 * MethodHanle在各类开源项目中大量使用，比如Mybatis
 *
 * @date: 2022/12/1 14:30
 */
public final class MethodAccessor {
    private static final Logger logger = LoggerFactory.getLogger(MethodAccessor.class);

    private static final MethodHandles.Lookup lookup = MethodHandles.lookup();

    private static <T> MethodHandle initMethodHandles(T target, String fieldName) {
        try {
            Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return lookup.unreflectSetter(field);
        } catch (Exception e) {
            logger.warn("MethodHandle初始化异常",e);
            throw new RuntimeException(e);
        }
    }

    public static <T> void set(T target, String fieldName, Object fieldValue){
        MethodHandle methodHandle = initMethodHandles(target, fieldName);
        try {
            // 由于fieldValue可能是非包装类型，这时候如果采用invokeExact会精确匹配参数，不会向上转型
            // 而采用invoke能够自动帮助基本类型向包装类型转型
            methodHandle.invoke(target, fieldValue);
        } catch (Throwable e) {
            logger.warn("MethodHanle赋值异常",e);
            throw new RuntimeException(e);
        }
    }
}
