package com.backend.boilerplate.dto.validator;

import com.backend.boilerplate.AbstractIT;
import com.backend.boilerplate.dto.CreateRoleDto;
import com.backend.boilerplate.entity.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author sarvesh
 * @version 0.0.1
 * @since 0.0.1
 */
@ExtendWith(SpringExtension.class)
@Disabled
public class UniqueRoleNameValidatorTest extends AbstractIT {

    @Autowired
    private LocalValidatorFactoryBean validator;

    @Autowired
    private TestEntityManager testEntityManager;

    private Role role;

    @BeforeEach
    void setup() {
        role = new Role();
        role.setName("Manager");
        role = testEntityManager.persistAndFlush(role);
    }

    @Test
    void createRole_shouldPass_noDuplicateName() {
        CreateRoleDto dto = prepareCreateRoleDto("Team Lead");
        Set<ConstraintViolation<CreateRoleDto>> constraintViolations = validator.validateProperty(dto, "name",
            Extended.class);
        assertTrue(constraintViolations.isEmpty());
    }

    @Test
    void createRole_shouldThrow_duplicateName() {
        CreateRoleDto dto = prepareCreateRoleDto("Manager");
        Set<ConstraintViolation<CreateRoleDto>> constraintViolations = validator.validateProperty(dto, "name",
            Extended.class);
        assertFalse(constraintViolations.isEmpty());
        assertThat(constraintViolations).hasSize(1);
        assertThat(constraintViolations.stream().findFirst().get().getMessage()).isEqualTo("1008");
    }

    private CreateRoleDto prepareCreateRoleDto(String roleName) {
        CreateRoleDto dto = new CreateRoleDto();
        dto.setName(roleName);
        //dto.getClaims().add(UUID.randomUUID());
        return dto;
    }
}
