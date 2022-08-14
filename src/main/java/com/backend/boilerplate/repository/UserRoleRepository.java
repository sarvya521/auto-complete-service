package com.backend.boilerplate.repository;

import com.backend.boilerplate.entity.Role;
import com.backend.boilerplate.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author sarvesh
 * @version 0.0.2
 * @since 0.0.1
 */
@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UserRole.UserRoleId> {

    List<UserRole> findByRole(Role role);

    /**
     * @param roles List of Role
     * @return List of UserRole
     * @since 0.0.6
     */
    List<UserRole> findByRoleIn(List<Role> roles);
}