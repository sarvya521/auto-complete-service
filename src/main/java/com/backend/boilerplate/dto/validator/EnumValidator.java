package com.backend.boilerplate.dto.validator;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author sarvesh
 * @version 0.0.2
 * @since 0.0.2
 */
@Component
public class EnumValidator implements ConstraintValidator<ValidEnum, String> {

    private List<String> acceptedValues = null;

    @Override
    public void initialize(ValidEnum constraintAnnotation) {
        acceptedValues = Stream.of(constraintAnnotation.enumClass().getEnumConstants())
            .map(Enum::name)
            .collect(Collectors.toList());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return StringUtils.isEmpty(value) || acceptedValues.contains(value.toUpperCase());
    }
}