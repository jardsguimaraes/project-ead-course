package com.ead.authuser.validations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = PasswordConstraintImpl.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordConstraint {

    String message() default """
            Invalid password! Password must contain at least onde digit[0-9],
            at least one lowercase Latin character [a-z], at least one uppercase
            Latin character [A-Z], at least one especial character
            like !@#&()-:;',?/*~$^+=<>, a length of at least 6 and a maximum of
            20 characters""";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
