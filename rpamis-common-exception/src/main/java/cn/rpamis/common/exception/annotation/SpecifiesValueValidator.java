package cn.rpamis.common.exception.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;

/**
 * 特定值校验器注解
 * 支持特定值int、str、枚举校验
 *
 * @author benym
 * @date 2022/10/31 18:55
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {SpecifiesValueValidatorImpl.class})
@Repeatable(SpecifiesValueValidator.List.class)
public @interface SpecifiesValueValidator {
    /**
     * 默认校验消息
     *
     * @return String
     */
    String message() default "入参必须为指定值";

    /**
     * 分组校验
     *
     * @return Class<?>[]
     */
    Class<?>[] groups() default {};

    /**
     * 负载
     *
     * @return Class<? extends Payload>[]
     */
    Class<? extends Payload>[] payload() default {};

    /**
     * 指定特定String值
     *
     * @return String[]
     */
    String[] strGroup() default {};

    /**
     * 指定特定int值
     *
     * @return int[]
     */
    int[] intGroup() default {};

    /**
     * 指定枚举类型
     *
     * @return Class<?>
     */
    Class<?> enumClass() default Class.class;

    /**
     * 可重复校验
     */
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        SpecifiesValueValidator[] value();
    }
}
