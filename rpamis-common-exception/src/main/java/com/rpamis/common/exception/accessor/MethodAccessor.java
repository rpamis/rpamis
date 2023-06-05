package com.rpamis.common.exception.accessor;

import com.rpamis.common.dto.exception.AbstractException;
import com.rpamis.common.dto.exception.ExceptionFactory;

import java.lang.invoke.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * MethodHandle在各类开源框架中大量使用，如Mybatis等
 * 为什么使用MethodHandle
 * MethodHandle性能压测如何
 *
 * @author benym
 * @date: 2022/12/1 14:30
 * @link {<a href="https://www.optaplanner.org/blog/2018/01/09/JavaReflectionButMuchFaster.html">...</a>}
 * <p/>
 * 对于类外进行私有变量的访问的场景
 * 在JDK8环境下
 * MethodHandle虽然单独有效，但和LambdaMetafactory.metafactory BiConsumer很难结合在一起
 * @link {<a href="https://stackoverflow.com/questions/28184065/java-8-access-private-member-with-lambda">...</a>}
 * 一种更hack的结合方法是采用MethodHandle的内部方法，但由于安全性和运行原理未知，暂不使用
 * @link {<a href="https://stackoverflow.com/questions/69068124/lambdametafactory-and-private-methods">...</a>}
 * 在JDK9环境下MethodHandle提供privateLookupIn则才能和LambdaMetafactory.metafactory结合
 * <p/>
 * tip: 即使不采用LambdaMetafactory方式做到更为通用的MethodHandle,
 * 简单的静态化MethodHandle仍然是通过底层native执行方法，压测性能接近原生，远快于反射
 * 如果使用时不采用static，则MethodHandle甚至慢于反射
 * 采用纳秒为单位，数值越小越快，从上至下，非静态化Mh、非静态化反射、静态化Mh、静态化反射
 * Benchmark                                              Mode  Cnt    Score    Error  Units
 * MethodHandleTest.MHBenchmark.testNoStaticMethodHandle  avgt   10  732.150 ± 40.476  ns/op
 * MethodHandleTest.MHBenchmark.testNoStaticReflection    avgt   10  439.412 ±  8.547  ns/op
 * MethodHandleTest.MHBenchmark.testStaticMethodHandle    avgt   10    1.561 ±  0.014  ns/op
 * MethodHandleTest.MHBenchmark.testStaticReflection      avgt   10   25.693 ±  0.543  ns/op
 * 对于非私有变量访问的场景，MethodHandle可以和LambdaMetafactory.metafactory任意lambda函数结合，做到更通用化，此处为和Function结合
 * 采用纳秒为单位，数值越小越快，从上至下，直接new set、lambda mh、无lambda的mh、反射
 * Benchmark                                      Mode  Cnt     Score     Error  Units
 * MhExceptionBenchMark.MhExceptioTest.directNew  avgt   10  2421.192 ± 165.195  ns/op
 * MhExceptionBenchMark.MhExceptioTest.mhLamda    avgt   10  2589.443 ± 204.428  ns/op
 * MhExceptionBenchMark.MhExceptioTest.mhNoLamda  avgt   10  2664.148 ± 217.869  ns/op
 * MhExceptionBenchMark.MhExceptioTest.reflet     avgt   10  2710.181 ± 304.747  ns/op
 */
public final class MethodAccessor {

    private static final MethodHandles.Lookup LOOKUP = MethodHandles.lookup();

    private static final MethodType METHOD_TYPE = MethodType.methodType(void.class, String.class);

    private static final ConcurrentHashMap<String, Function<String, AbstractException>> CACHE_FUNCTION = new ConcurrentHashMap<>();

    /**
     * 方法句柄是一个有类型的，可以直接执行的指向底层方法、构造器、field等的引用
     * 可以简单理解为函数指针，它是一种更加底层的查找、调整和调用方法的机制
     * <p>
     * 提供比反射更高效的映射方法
     * 效率上与原生调用仅有纳秒级差距
     *
     * @param cls     动态推断的class
     * @param message 需要抛出给前端的信息
     * @param <T>     class类型
     * @return AbstractException或其子类
     */
    public static <T extends AbstractException> AbstractException getException(Class<T> cls, String message) {
        try {
            Function<String, AbstractException> function = CACHE_FUNCTION.get(cls.toString());
            if (function != null) {
                return applyMessage(function, message);
            }
            function = MethodAccessor.createConstruct(cls);
            CACHE_FUNCTION.putIfAbsent(cls.toString(), function);
            return applyMessage(function, message);
        } catch (Exception e) {
            throw ExceptionFactory.sysException("获取cache exception异常", e);
        }
    }

    /**
     * 参考@link{<a href="https://stackoverflow.com/questions/53675777/how-to-instantiate-an-object-using-lambdametafactory">...</a>}
     * 根据异常Class，动态通过LambdaMetafactory寻找构造函数
     *
     * @param cls 异常Class
     * @param <T> 异常Class类型
     * @return Function<String, AbstractException>
     */
    @SuppressWarnings("unchecked")
    public static <T> Function<String, AbstractException> createConstruct(Class<T> cls) {
        try {
            MethodHandle methodHandle = LOOKUP.findConstructor(cls, METHOD_TYPE);
            CallSite site = LambdaMetafactory.metafactory(
                    LOOKUP,
                    "apply",
                    MethodType.methodType(Function.class),
                    methodHandle.type().generic(),
                    methodHandle,
                    methodHandle.type());
            return (Function<String, AbstractException>) site.getTarget().invokeExact();
        } catch (Throwable e) {
            throw ExceptionFactory.sysException("LambdaMetafactory create construct异常:", e);
        }
    }

    /**
     * 根据Function函数和异常message，调用对应构造函数方法
     *
     * @param function function函数
     * @param message  异常消息
     * @return AbstractException
     */
    public static AbstractException applyMessage(Function<String, AbstractException> function, String message) {
        try {
            return function.apply(message);
        } catch (Exception e) {
            throw ExceptionFactory.sysException("LambdaMetafactory function apply异常:", e);
        }
    }

    private MethodAccessor() {
        throw new IllegalStateException("常量类，禁止实例化");
    }
}
