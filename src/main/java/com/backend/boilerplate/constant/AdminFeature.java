package com.backend.boilerplate.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author sarvesh
 * @version 0.0.2
 * @since 0.0.2
 */
@RequiredArgsConstructor
public enum AdminFeature {
    ROLE("Role"),
    USER("User");

    @Getter
    private final String name;

    @RequiredArgsConstructor
    public enum Restricted {
        ROLE("Role");

        @Getter
        private final String name;
    }
}
