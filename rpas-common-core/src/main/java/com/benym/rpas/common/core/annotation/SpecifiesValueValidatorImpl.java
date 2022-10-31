package com.benym.rpas.common.core.annotation;

import com.benym.rpas.common.dto.exception.ExceptionFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Method;

/**
 * 特定值校验器实现
 *
 * @Time:2022/10/31 19:37
 */
public class SpecifiesValueValidatorImpl implements ConstraintValidator<SpecifiesValueValidator, Object> {

    private String[] strGroup;

    private int[] intGroup;

    private Class<?> enumClass;

    @Override
    public void initialize(SpecifiesValueValidator constraintAnnotation) {
        strGroup = constraintAnnotation.strGroup();
        intGroup = constraintAnnotation.intGroup();
        enumClass = constraintAnnotation.enumClass();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            if (null == value) {
                return true;
            }
            if (enumClass.isEnum()) {
                Object[] enumConstants = enumClass.getEnumConstants();
                for (Object enumConstant : enumConstants) {
                    Method method = enumClass.getDeclaredMethod("getCode");
                    Object invokeResult = method.invoke(enumConstant);
                    if (invokeResult.equals(value)) {
                        return true;
                    }
                }
            }
            if (value instanceof String) {
                for (String str : strGroup) {
                    if (str.equals(value)) {
                        return true;
                    }
                }
            }
            if (value instanceof Integer) {
                for (Integer ints : intGroup) {
                    if (ints == value) {
                        return true;
                    }
                }
            }
        } catch (NoSuchMethodException e) {
            throw ExceptionFactory.bizException("该枚举类没有getCode方法", e);
        } catch (Exception e) {
            throw ExceptionFactory.bizException("特定值校验器异常", e);
        }
        return false;
    }
}
