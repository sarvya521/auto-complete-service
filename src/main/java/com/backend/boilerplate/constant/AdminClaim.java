package com.backend.boilerplate.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum holding all possible claims for any Admin User
 *
 * @author sarvesh
 * @version 0.0.2
 * @since 0.0.2
 */
@Getter
@RequiredArgsConstructor
public enum AdminClaim {
    CREATE_ROLE("CreateRoles"),
    CREATE_USER("CreateUsers"), READ_USER("GetAllUsersForCurrentUser");

    private final String claim;
}
