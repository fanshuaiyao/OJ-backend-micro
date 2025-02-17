package com.yupi.yuojbackendmodel.model.Anno;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
/**
 * @className: PasswordMatches
 * @author: fanshuaiyao
 * @description: 此注解用来检验用户注册密码的一致性
 * @date: 2025/2/17 16:32
 */
@Documented
@Constraint(validatedBy = PasswordMatchesValidator.class)
@Target({TYPE})
@Retention(RUNTIME)
public @interface PasswordMatches {
    String message() default "两次输入的密码不一致";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}