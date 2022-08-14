package com.backend.boilerplate.exception;

import com.sp.boilerplate.commons.util.ErrorGenerator;

/**
 * @author sarvesh
 * @version 0.0.1
 * @since 0.0.1
 */
public class UserNotFoundException extends UserManagementException {
    private static final long serialVersionUID = 1L;

    public UserNotFoundException() {
        super(ErrorGenerator.generateForCode("1004"));
    }
}
