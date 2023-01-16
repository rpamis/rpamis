package com.benym.rpas.common.exception.annotation;

import com.benym.rpas.common.dto.exception.ExceptionFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
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

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            if (null == value) {
                return true;
            }
            if (enumClass.isEnum()) {
                // 获取传入的枚举class的所有定义的枚举，反射获取code判断是否和入参相同
                Object[] enumConstants = enumClass.getEnumConstants();
                for (Object enumConstant : enumConstants) {
                    Method method = enumClass.getDeclaredMethod("getCode");
                    Object invokeResult = method.invoke(enumConstant);
                    if (invokeResult.equals(value)) {
                        return true;
                    }
                }
            } else if (value instanceof String) {
                if (strSet.contains(value)) {
                    return true;
                }
            } else if (value instanceof Integer) {
                if (intSet.contains(value)) {
                    return true;
                }
            } else if (value instanceof List) {
                for (Object v : (List<?>) value) {
                    String cast = (String) v;
                    if (!strSet.contains(cast)) {
                        return false;
                    }
                }
                return true;
            }
        } catch (NoSuchMethodException e) {
            throw ExceptionFactory.sysException("该枚举类没有getCode方法", e);
        } catch (Exception e) {
            throw ExceptionFactory.sysException("特定值校验器异常", e);
        }
        return false;
    }
}
