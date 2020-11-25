package com.backend.boilerplate.dto.validator;

import com.backend.boilerplate.dao.UserRepository;
import com.backend.boilerplate.dto.UpdateUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.UUID;

/**
 * @author sarvesh
 * @version 0.0.2
 * @since 0.0.2
 */
@Component
public class UniqueUserValidator implements ConstraintValidator<UniqueResource, UpdateUserDto> {

    @Autowired
    private UserRepository userRepository;

    @Override
    @SuppressWarnings("squid:S3655")
    public boolean isValid(UpdateUserDto updateUserDto, ConstraintValidatorContext context) {
        UUID uuid = updateUserDto.getUuid();
        if (!userRepository.existsByUuid(uuid)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("1004")
                .addPropertyNode("uuid")
                .addConstraintViolation();
            return false;
        }

        if (userRepository.existsByUuidNotAndEmailIgnoreCase(uuid, updateUserDto.getEmail())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("1061")
                .addPropertyNode("email")
                .addConstraintViolation();
            return false;
        }

        return true;
    }
}