package com.presidentio.teamcity.jmh.runner.common.cons;

/**
 * Created by presidentio on 07.05.15.
 */
public class Dictionary {

    public static final String PATH_TO_JMH_JAR = "Path to jmh jar";
    public static final String MODE = "Mode";
    public static final String TAB_TITLE = "Benchmarks";
    public static final String DISPLAY_NAME = "Jmh";
    public static final String DESCRIPTION = "Jmh runner";
    public static final String ERROR_JAR_PATH_EMPTY = "Jar path must be specified";
    public static final String ERROR_MODE_ILLEGAL = "Mode must be one of: " + ModeConst.MODES;
    public static final String ERROR_TIME_UNIT_ILLEGAL = "Time unit must be one of: " + TimeUnitConst.TIME_UNITS;
    public static final String ERROR_MUST_BE_ONE_OF = "Value must be one of: ";
    public static final String ERROR_FAILED_TO_PARSE_BENCHMARK = "Failed to load benchmarks for this build";
    public static final String ERROR_PARAMETER_IS_REQUIRED = "Parameter is required";
    public static final String ERROR_NUMBER = "Must be number";
    public static final String ERROR_NUMBER_LESS = "Must be less then %d";
    public static final String ERROR_NUMBER_MORE = "Must be more then %d";

}
