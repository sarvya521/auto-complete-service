package com.backend.boilerplate.dto.validator;

import com.backend.boilerplate.constant.Role;
import com.backend.boilerplate.repository.RoleRepository;
import com.backend.boilerplate.repository.UserRepository;
import com.backend.boilerplate.dto.CreateUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Optional;

/**
 * @author sarvesh
 * @version 0.0.2
 * @since 0.0.2
 */
@Component
public class CreateUserValidator implements ConstraintValidator<ValidCreateResource, CreateUserDto> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    @SuppressWarnings("squid:S3655")
    public boolean isValid(CreateUserDto createUserDto, ConstraintValidatorContext context) {
        Optional<Long> longOptional = userRepository.countByEmailIgnoreCase(createUserDto.getEmail());
        if (longOptional.get() > 0) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("1061")
                .addPropertyNode("email")
                .addConstraintViolation();
            return false;
        }

        List<String> roleNames = roleRepository.findNameByUuidIn(createUserDto.getRoles());
        if (roleNames.size() != createUserDto.getRoles().size()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("1009")
                .addPropertyNode("roles")
                .addConstraintViolation();
            return false;
        }

        final long adminRoleCount = roleNames.stream()
            .filter(Role::isAdmin)
            .count();
        if (adminRoleCount > 1) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("1012")
                .addPropertyNode("roles")
                .addConstraintViolation();
            return false;
        }

        return true;
    }


}