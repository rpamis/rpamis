package com.benym.rpas.common.dto.exception;

import com.benym.rpas.common.dto.enums.StatusCode;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 异常工厂
 *
 * @Time : 2022/7/7 22:46
 */
public class ExceptionFactory {

    private static final MethodHandles.Lookup publicLookup = MethodHandles.publicLookup();

    private static final MethodType methodType = MethodType.methodType(void.class, String.class);

    private static final ConcurrentHashMap<String, MethodHandle> cacheException = new ConcurrentHashMap<>();

    /**
     * 方法句柄是一个有类型的，可以直接执行的指向底层方法、构造器、field等的引用
     * 可以简单理解为函数指针，它是一种更加底层的查找、调整和调用方法的机制
     *
     * 提供比反射更高效的映射方法
     * 效率上与原生调用仅有纳秒级差距
     *
     * @param cls 动态推断的class
     * @param message 需要抛出给前端的信息
     * @param <T> class类型
     * @return AbstractException或其子类
     */
    public static <T extends AbstractException> AbstractException getException(Class<T> cls, String message) {
        try {
            MethodHandle methodHandle = cacheException.get(cls.toString());
            if (methodHandle != null) {
                return (AbstractException) methodHandle.invoke(message);
            }
            methodHandle = publicLookup.findConstructor(cls, methodType);
            cacheException.putIfAbsent(cls.toString(), methodHandle);
            Object invoke = methodHandle.invoke(message);
            return (AbstractException) invoke;
        } catch (Throwable throwable) {
            throw sysException("获取cache exception异常", throwable);
        }
    }

    public static BizException bizException() {
        return new BizException();
    }

    public static BizException bizException(String errMessage) {
        return new BizException(errMessage);
    }

    public static BizException bizException(String errCode, String errMessage) {
        return new BizException(errCode, errMessage);
    }

    public static BizException bizException(String errMessage, Throwable e) {
        return new BizException(errMessage, e);
    }

    public static BizException bizException(String errCode, String errMessage, Throwable e) {
        return new BizException(errCode, errMessage, e);
    }

    public static BizException bizException(Throwable e) {
        return new BizException(e);
    }

    public static SysException sysException() {
        return new SysException();
    }

    public static SysException sysException(String errMessage) {
        return new SysException(errMessage);
    }

    public static SysException sysException(Throwable e) {
        return new SysException(e);
    }

    public static SysException sysException(String errCode, String errMessage, Throwable e) {
        return new SysException(errCode, errMessage, e);
    }

    public static SysException sysException(String errCode, String errMessage) {
        return new SysException(errCode, errMessage);
    }

    public static SysException sysException(String errMessage, Throwable e) {
        return new SysException(errMessage, e);
    }

    public static RpasException rpasException() {
        return new RpasException();
    }

    public static RpasException rpasException(String errCode, String errMessage, String detailMessage) {
        return new RpasException(errCode, errMessage, detailMessage);
    }

    public static RpasException rpasException(String errCode, String errMessage) {
        return new RpasException(errCode, errMessage);
    }

    public static RpasException rpasException(StatusCode statusCode) {
        return new RpasException(statusCode);
    }

    public static RpasException rpasException(StatusCode statusCode, String detailMessage) {
        return new RpasException(statusCode, detailMessage);
    }

    public static ValidException validException(String errMessage) {
        return new ValidException(errMessage);
    }

    public static ValidException validException(String errCode, String errMessage) {
        return new ValidException(errCode, errMessage);
    }

    public static ValidException validException(StatusCode statusCode) {
        return new ValidException(statusCode);
    }
}
