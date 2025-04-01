package com.waystech.authmanagement.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserTypeValidator.class)
@Documented
public @interface ValidUserType {

    String message() default "User type must be user or manager";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

