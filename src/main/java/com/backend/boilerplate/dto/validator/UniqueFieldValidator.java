package com.backend.boilerplate.dto.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintValidatorFactory;

/**
 * @author sarvesh
 * @version 0.0.2
 * @since 0.0.2
 */
@Component
public class UniqueFieldValidator implements ConstraintValidator<UniqueField, Object> {

    @Autowired
    private ConstraintValidatorFactory constraintValidatorFactory;

    @SuppressWarnings("squid:S3740")
    private ConstraintValidator constraintValidator;

    @SuppressWarnings("unchecked")
    @Override
    public void initialize(UniqueField constraintAnnotation) {
        constraintValidator = constraintValidatorFactory.getInstance(constraintAnnotation.constraintValidator());
        constraintValidator.initialize(constraintAnnotation);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return constraintValidator.isValid(value, context);
    }
}