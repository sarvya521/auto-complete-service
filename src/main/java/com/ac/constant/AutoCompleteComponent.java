package com.ac.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * Holds mapping {@link #AC_SERVICE} of <b>auto-complete service type</b> as
 * {@code key} and its related {@link com.ac.service.AutoCompleteService}
 * implementation&#39;s {@code Qualifier} as {@code value}
 *
 * <p>
 * <b>auto-complete service type</b> {@code key of AC_SERVICE} should be exactly
 * same as {@code PathVariable} of search API.
 *
 * @see com.ac.service.AutoCompleteService
 * @see com.ac.service.impl.AutoCompleteSvcFacade#search
 *
 * @author sarvesh
 */
public enum AutoCompleteComponent {
    INSTANCE;
    
    public static final String CITY = "city";
    public static final String STATE = "state";

    private static final Map<String, String> AC_SERVICE = new HashMap<>();
    static {
        AC_SERVICE.put("city", "cityAutoCompleteService");
        AC_SERVICE.put("state", "stateAutoCompleteService");
    }

    public static String getService(String type) {
        return AC_SERVICE.get(type);
    }
}
