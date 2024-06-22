package com.neonlab.common.constants;

/**
 * This class contains all the constants that are observed to be user across repository and are common
 * This can grow incrementally as some of the constants are observed to be common.
 *
 * Note: This class should only contain constants and not any code logic
 */

public class GlobalConstants {

    public static String SYSTEM = "login-service";
    public static String ERROR_OCCURRED = "Error: {}";
    public static String CREATED_AT = "createdAt";

    public static String TOKEN = "token";

    public static String EMPTY_STRING = "";
    public static final String MULTIPART_FORM_DATA = "multipart/form-data";

    // the below class only stores the symbols constants
    public static class Symbols{

        public static String DOUBLE_COLON = "::";
        public static String COMMA = ",";
        public static String PERCENTAGE = "%";

    }

}
