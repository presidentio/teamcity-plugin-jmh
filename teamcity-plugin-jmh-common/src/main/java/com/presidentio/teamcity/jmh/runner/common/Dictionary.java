package com.presidentio.teamcity.jmh.runner.common;

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
    public static final String ERROR_FAILED_TO_PARSE_BENCHMARK = "Failed to load benchmarks for this build";

}
