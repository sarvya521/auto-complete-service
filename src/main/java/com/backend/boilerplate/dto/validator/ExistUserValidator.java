package com.backend.boilerplate.dto.validator;

import com.backend.boilerplate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.UUID;

/**
 * @author sarvesh
 * @version 0.0.1
 * @since 0.0.1
 */
@Component
public class ExistUserValidator implements ConstraintValidator<Exist, UUID> {

    @Autowired
    private UserRepository userRepository;

    @SuppressWarnings("squid:S3655")
    @Override
    public boolean isValid(UUID userUuid, ConstraintValidatorContext context) {
        return userRepository.existsByUuid(userUuid);
    }
}