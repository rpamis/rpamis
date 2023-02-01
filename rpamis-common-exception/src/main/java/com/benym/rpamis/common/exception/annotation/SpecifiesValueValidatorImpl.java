package com.benym.rpamis.common.exception.annotation;

import com.benym.rpamis.common.dto.exception.ExceptionFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 特定值校验器实现
 *
 * @author benym
 * @date 2022/10/31 19:37
 */
public class SpecifiesValueValidatorImpl implements ConstraintValidator<SpecifiesValueValidator, Object> {

    private Class<?> enumClass;

    private HashSet<String> strSet;

    private Set<Integer> intSet;

    @Override
    public void initialize(SpecifiesValueValidator constraintAnnotation) {
        String[] strGroup = constraintAnnotation.strGroup();
        strSet = new HashSet<>(Arrays.asList(strGroup));
        int[] intGroup = constraintAnnotation.intGroup();
        intSet = Arrays.stream(intGroup).boxed().collect(Collectors.toSet());
        enumClass = constraintAnnotation.enumClass();
    }

    /**
     * 此时value为被注解的字段类型
     *
     * @param value   object to validate
     * @param context context in which the constraint is evaluated
     * @return boolean
     */
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            if (null == value) {
                return true;
            }
            if (enumClass.isEnum()) {
                return validEnum(value, enumClass);
            }
            if (value instanceof String && strSet.contains(value)) {
                return true;
            }
            if (value instanceof Integer && intSet.contains(value)) {
                return true;
            }
            if (value instanceof List) {
                return validList(value, strSet);
            }
        } catch (NoSuchMethodException e) {
            throw ExceptionFactory.sysException(enumClass + "枚举类没有getCode方法", e);
        } catch (Exception e) {
            throw ExceptionFactory.sysException("特定值校验器异常", e);
        }
        return false;
    }

    public static boolean validEnum(Object value, Class<?> enumClass) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        // 获取传入的枚举class的所有定义的枚举，反射获取code判断是否和入参相同
        Object[] enumConstants = enumClass.getEnumConstants();
        for (Object enumConstant : enumConstants) {
            Method method = enumClass.getDeclaredMethod("getCode");
            Object invokeResult = method.invoke(enumConstant);
            if (invokeResult.equals(value)) {
                return true;
            }
        }
        return false;
    }

    public static boolean validList(Object value, Set<String> strSet) {
        for (Object v : (List<?>) value) {
            String cast = (String) v;
            if (!strSet.contains(cast)) {
                return false;
            }
        }
        return true;
    }
}
