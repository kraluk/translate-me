package com.lid.intellij.translateme.common;

/**
 * Simple class storing application wide constants
 *
 * @author lukasz
 */
public final class ApplicationConstant {

    private ApplicationConstant() {
        throw new UnsupportedOperationException("Class cannot be instantiated!");
    }

    public static final String DEFAULT_SOURCE_LANGUAGE = "en";

    public static final String DEFAULT_TARGET_LANGUAGE = "pl";

    public static final String DEFAULT_UI_LANGUAGE = "en";
}
