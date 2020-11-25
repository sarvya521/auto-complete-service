package com.backend.boilerplate.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author sarvesh
 * @version 0.0.2
 * @since 0.0.1
 */
@RequiredArgsConstructor
public enum Role {
    DEFAULT("DEFAULT"),
    SYSTEM_ADMIN("System Admin"),
    SUPER_ADMIN("Super Admin");

    @Getter
    private final String name;

    public static final List<String> ORDERED_STATIC_ROLES =
        List.of(
            SYSTEM_ADMIN.getName(),
            SUPER_ADMIN.getName()
        );

    public static boolean isAdmin(String role) {
        return ORDERED_STATIC_ROLES.contains(role);
    }

    public static Optional<Role> fromName(String name) {
        return
            Stream.of(values())
                .filter(role -> role.getName().equalsIgnoreCase(name))
                .findFirst();
    }
}
