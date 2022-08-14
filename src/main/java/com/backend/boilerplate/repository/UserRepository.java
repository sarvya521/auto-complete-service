package com.backend.boilerplate.repository;

import com.backend.boilerplate.entity.Role;
import com.backend.boilerplate.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(UserRepositoryQuery.QUERY_FIND_ID_BY_UUID)
    Optional<Long> findIdByUuid(@Param("uuid") UUID uuid);

    Optional<User> findByUuid(UUID uuid);

    Optional<Long> countByEmailIgnoreCase(String email);

    Integer countByUuid(UUID uuid);

    boolean existsByUuid(UUID uuid);


    Optional<Long> countByUuidNotAndEmailIgnoreCase(UUID uuid, String email);

    boolean existsByUuidNotAndEmailIgnoreCase(UUID uuid, String email);

    Optional<Integer> countByUuidIn(List<UUID> userUuids);

    List<User> findByUuidIn(List<UUID> uuids);

    @Query(UserRepositoryQuery.QUERY_FIND_BY_USERROLES_NOT_CONTAINING)
    List<User> findByUserRolesNotContaining(@Param("userId") Long userId,
                                            @Param("roleNames") List<String> roleNames);

    @Query(UserRepositoryQuery.QUERY_FIND_BY_USERROLES_CONTAINING)
    Page<User> findByUserRolesContaining(@Param("userId") Long userId,
                                         @Param("roleNames") List<String> roleNames,
                                         Pageable pageable);

    @Query(UserRepositoryQuery.QUERY_FIND_BY_USERROLES_NOT_CONTAINING)
    Page<User> findByUserRolesNotContaining(@Param("userId") Long userId,
                                            @Param("roleNames") List<String> roleNames,
                                            Pageable pageable);

    @Query(UserRepositoryQuery.QUERY_FIND_BY_USERROLES_CONTAINING)
    List<User> findByUserRolesContaining(@Param("userId") Long userId,
                                         @Param("roleNames") List<String> roleNames);

    @Query(UserRepositoryQuery.QUERY_GET_USER_ROLES)
    List<RoleUser> getRoleUser(@Param("roleNames") List<String> roleNames);

    interface RoleUser {
        Role getRole();

        User getUser();
    }
}
