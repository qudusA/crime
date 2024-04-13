package org.fexisaf.crimerecordmanagementsystem.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;


@Documented
@Constraint(validatedBy =FineAmountValidator.class)
@Target({TYPE})
@Retention(RetentionPolicy.RUNTIME)
public  @interface FineAmountRequired {
    String message() default "Fine amount is required when punishment type is 'fine'";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

