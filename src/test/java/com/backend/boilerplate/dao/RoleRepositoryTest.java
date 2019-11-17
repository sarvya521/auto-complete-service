/*
 * Copyright (c) 2019 www.roche.com.
 * All rights reserved.
 */

package com.backend.boilerplate.dao;

import com.backend.boilerplate.TestBoilerplateServiceApplication;
import com.backend.boilerplate.entity.Role;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.backend.boilerplate.entity.Status.CREATED;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author sarvesh
 * @version 0.0.1
 * @since 0.0.1
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
@ContextConfiguration(classes = {TestBoilerplateServiceApplication.class})
@AutoConfigureEmbeddedDatabase
@ActiveProfiles("embeddedpostgres")
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    private static final Long PERFORMED_BY = -1L;

    @Test
    public void saveRole_shouldPass() {
        /*********** Setup ************/
        Role role = Role.builder()
            .name("Manager")
            .status(CREATED)
            .performedBy(PERFORMED_BY)
            .build();

        /*********** Execute ************/
        role = roleRepository.saveAndFlush(role);

        /*********** Verify/Assertions ************/
        assertNotNull(role.getId());
    }
}
