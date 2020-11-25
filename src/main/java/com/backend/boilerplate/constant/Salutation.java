package com.backend.boilerplate.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author sarvesh
 * @version 0.0.2
 * @since 0.0.2
 */
@RequiredArgsConstructor
public enum Salutation {
    MR("Mr"), MISS("Miss"), MRS("Mrs");

    @Getter
    private final String code;
}
