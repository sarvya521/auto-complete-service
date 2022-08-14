package com.backend.boilerplate.repository;

import com.backend.boilerplate.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author sarvesh
 * @version 0.0.2
 * @since 0.0.1
 */
@SuppressWarnings("squid:S100")
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByUuid(UUID uuid);

    Optional<Role> findByNameIgnoreCase(String name);

    List<Role> findAllByUuidIn(Iterable<UUID> uuids);

    boolean existsByUuid(UUID uuid);

    /**
     * @param names List of String
     * @return List of Role
     * @since 0.0.6
     */
    List<Role> findByNameIn(List<String> names);

    /**
     * @param uuids List of UUID
     * @return count of Roles
     * @since 0.0.6
     */
    Long countByUuidIn(List<UUID> uuids);

    /**
     * @param uuids List of UUID
     * @return List of role names
     * @since 0.0.6
     */
    @Query("SELECT name FROM Role WHERE uuid IN (:uuids)")
    List<String> findNameByUuidIn(@Param("uuids") List<UUID> uuids);

    boolean existsByUuidInAndRoleFeatures_ModuleFeaturesNameIn(List<UUID> roleNames, List<String> name);

    boolean existsByNameInAndRoleFeatures_ModuleFeaturesNameIn(List<String> roleNames, List<String> name);

    boolean existsByName(String name);
}
