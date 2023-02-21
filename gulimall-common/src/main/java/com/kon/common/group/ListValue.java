package com.kon.common.group;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Documented
// 关联校验器
@Constraint(validatedBy = { ListValueConstraintValidator.class })

public @interface ListValue {

    // 我们可以指定一个默认的提示信息
    String message() default "{com.kon.common.validator.ListValue.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    // 提供的能够校验通过的值
    int[] vals() default { };
}
