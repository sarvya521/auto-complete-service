package com.backend.boilerplate;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * @author sarvesh
 * @version 0.0.2
 * @since 0.0.2
 */
public class StringUtils {
    public static String generateAlias(int targetStringLength) {
        return "a" + RandomStringUtils.random(targetStringLength - 1, "abcdefghijklmnopqrstuvwxyz-");
    }

    public static String generateName(int targetStringLength) {
        return RandomStringUtils.random(targetStringLength, "abcdefghijklmnopqrstuvwxyz");
    }
}
