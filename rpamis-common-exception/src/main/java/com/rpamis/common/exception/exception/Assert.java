package com.rpamis.common.exception.exception;

import com.rpamis.common.exception.accessor.MethodAccessor;
import com.rpamis.common.dto.exception.*;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Map;

/**
 * 参考 {@link org.springframework.util.Assert}
 * 包装自定义异常，用于业务上减少if判断后手动从ExceptionFactory抛异常的情况
 * 适配class动态推断
 *
 * @author benym
 * @date 2022/11/11 20:35
 */
public abstract class Assert {

    private Assert() {
        throw new IllegalStateException("抽象类，禁止实例化");
    }

    private static <T> void preCheck(Class<T> cls) {
        if (cls == BizException.class || cls == SysException.class || cls == RpamisException.class) {
            throw new IllegalArgumentException(cls + "为不支持的推断类");
        }
    }

    public static void state(boolean expression, String message) {
        if (!expression) {
            throw ExceptionFactory.validException(message);
        }
    }

    public static <T extends AbstractException> void state(boolean expression, String message, Class<T> cls) {
        preCheck(cls);
        AbstractException exception = MethodAccessor.getException(cls, message);
        if (!expression) {
            throw exception;
        }
    }

    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw ExceptionFactory.validException(message);
        }
    }

    public static <T extends AbstractException> void isTrue(boolean expression, String message, Class<T> cls) {
        preCheck(cls);
        AbstractException exception = MethodAccessor.getException(cls, message);
        if (!expression) {
            throw exception;
        }
    }

    public static void isNull(Object object, String message) {
        if (object != null) {
            throw ExceptionFactory.validException(message);
        }
    }

    public static <T extends AbstractException> void isNull(Object object, String message, Class<T> cls) {
        preCheck(cls);
        AbstractException exception = MethodAccessor.getException(cls, message);
        if (object != null) {
            throw exception;
        }
    }

    public static void notNull(Object object, String message) {
        if (object == null) {
            throw ExceptionFactory.validException(message);
        }
    }

    public static <T extends AbstractException> void notNull(Object object, String message, Class<T> cls) {
        preCheck(cls);
        AbstractException exception = MethodAccessor.getException(cls, message);
        if (object == null) {
            throw exception;
        }
    }

    public static void hasLength(String text, String message) {
        if (!StringUtils.hasLength(text)) {
            throw ExceptionFactory.validException(message);
        }
    }

    public static <T extends AbstractException> void hasLength(String text, String message, Class<T> cls) {
        preCheck(cls);
        AbstractException exception = MethodAccessor.getException(cls, message);
        if (!StringUtils.hasLength(text)) {
            throw exception;
        }
    }

    public static void hasText(String text, String message) {
        if (!StringUtils.hasText(text)) {
            throw ExceptionFactory.validException(message);
        }
    }

    public static <T extends AbstractException> void hasText(String text, String message, Class<T> cls) {
        preCheck(cls);
        AbstractException exception = MethodAccessor.getException(cls, message);
        if (!StringUtils.hasText(text)) {
            throw exception;
        }
    }

    public static void doesNotContain(String textToSearch, String substring, String message) {
        if (StringUtils.hasLength(textToSearch) && StringUtils.hasLength(substring) && textToSearch.contains(substring)) {
            throw ExceptionFactory.validException(message);
        }
    }

    public static <T extends AbstractException> void doesNotContain(String textToSearch, String substring, String message, Class<T> cls) {
        preCheck(cls);
        AbstractException exception = MethodAccessor.getException(cls, message);
        if (StringUtils.hasLength(textToSearch) && StringUtils.hasLength(substring) && textToSearch.contains(substring)) {
            throw exception;
        }
    }

    public static void notEmpty(Object[] array, String message) {
        if (ObjectUtils.isEmpty(array)) {
            throw ExceptionFactory.validException(message);
        }
    }

    public static <T extends AbstractException> void notEmpty(Object[] array, String message, Class<T> cls) {
        preCheck(cls);
        AbstractException exception = MethodAccessor.getException(cls, message);
        if (ObjectUtils.isEmpty(array)) {
            throw exception;
        }
    }

    public static void noNullElements(Object[] array, String message) {
        if (array != null) {
            for (Object element : array) {
                if (element == null) {
                    throw ExceptionFactory.validException(message);
                }
            }
        }
    }

    public static <T extends AbstractException> void noNullElements(Object[] array, String message, Class<T> cls) {
        preCheck(cls);
        AbstractException exception = MethodAccessor.getException(cls, message);
        if (array != null) {
            for (Object element : array) {
                if (element == null) {
                    throw exception;
                }
            }
        }

    }

    public static void notEmpty(Collection<?> collection, String message) {
        if (CollectionUtils.isEmpty(collection)) {
            throw ExceptionFactory.validException(message);
        }
    }

    public static <T extends AbstractException> void notEmpty(Collection<?> collection, String message, Class<T> cls) {
        preCheck(cls);
        AbstractException exception = MethodAccessor.getException(cls, message);
        if (CollectionUtils.isEmpty(collection)) {
            throw exception;
        }
    }

    public static void notEmpty(Map<?, ?> map, String message) {
        if (CollectionUtils.isEmpty(map)) {
            throw ExceptionFactory.validException(message);
        }
    }

    public static <T extends AbstractException> void notEmpty(Map<?, ?> map, String message, Class<T> cls) {
        preCheck(cls);
        AbstractException exception = MethodAccessor.getException(cls, message);
        if (CollectionUtils.isEmpty(map)) {
            throw exception;
        }
    }

    public static void isInstanceOf(Class<?> type, Object obj, String message) {
        notNull(type, "Type to check against must not be null");
        if (!type.isInstance(obj)) {
            instanceCheckFailed(type, obj, message);
        }

    }

    public static void isInstanceOf(Class<?> type, Object obj) {
        isInstanceOf(type, obj, "");
    }

    public static void isAssignable(Class<?> superType, Class<?> subType, String message) {
        notNull(superType, "Super type to check against must not be null");
        if (subType == null || !superType.isAssignableFrom(subType)) {
            assignableCheckFailed(superType, subType, message);
        }

    }

    public static void isAssignable(Class<?> superType, Class<?> subType) {
        isAssignable(superType, subType, "");
    }

    private static void instanceCheckFailed(Class<?> type, Object obj, String msg) {
        String className = obj != null ? obj.getClass().getName() : "null";
        String result = "";
        boolean defaultMessage = true;
        if (StringUtils.hasLength(msg)) {
            if (endsWithSeparator(msg)) {
                result = msg + " ";
            } else {
                result = messageWithTypeName(msg, className);
                defaultMessage = false;
            }
        }

        if (defaultMessage) {
            result = result + "Object of class [" + className + "] must be an instance of " + type;
        }

        throw ExceptionFactory.validException(result);
    }

    private static void assignableCheckFailed(Class<?> superType, Class<?> subType, String msg) {
        String result = "";
        boolean defaultMessage = true;
        if (StringUtils.hasLength(msg)) {
            if (endsWithSeparator(msg)) {
                result = msg + " ";
            } else {
                result = messageWithTypeName(msg, subType);
                defaultMessage = false;
            }
        }

        if (defaultMessage) {
            result = result + subType + " is not assignable to " + superType;
        }

        throw ExceptionFactory.validException(result);
    }

    private static boolean endsWithSeparator(String msg) {
        return msg.endsWith(":") || msg.endsWith(";") || msg.endsWith(",") || msg.endsWith(".");
    }

    private static String messageWithTypeName(String msg, Object typeName) {
        return msg + (msg.endsWith(" ") ? "" : ": ") + typeName;
    }
}
