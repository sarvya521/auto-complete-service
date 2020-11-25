package com.backend.boilerplate.dto.validator;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author sarvesh
 * @version 0.0.2
 * @since 0.0.2
 */
@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = {UniqueFieldValidator.class})
@ReportAsSingleViolation
@Documented
public @interface UniqueField {

    String message() default "1000";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @SuppressWarnings("squid:S1452")
    Class<? extends ConstraintValidator<UniqueField, ?>> constraintValidator();
}