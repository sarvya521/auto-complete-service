package com.backend.boilerplate.dao;

import com.backend.boilerplate.entity.Role;
import com.backend.boilerplate.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author sarvesh
 * @version 0.0.1
 * @since 0.0.1
 */
@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UserRole.UserRoleId> {

    List<UserRole> findByRole(Role role);
}
