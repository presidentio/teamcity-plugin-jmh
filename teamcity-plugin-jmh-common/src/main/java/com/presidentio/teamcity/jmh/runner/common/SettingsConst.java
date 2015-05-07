package com.presidentio.teamcity.jmh.runner.common;

/**
 * Created by presidentio on 07.05.15.
 */
public class SettingsConst {

    public static final String PROP_JAR_PATH = "jar_path";
    public static final String PROP_BENCHMARKS = "benchmarks";
    public static final String PROP_MODE = "mode";
    public static final String PROP_TIME_UNIT = "time_unit";

    public String getPROP_JAR_PATH() {
        return PROP_JAR_PATH;
    }

    public String getPROP_BENCHMARKS() {
        return PROP_BENCHMARKS;
    }

    public String getPROP_MODE() {
        return PROP_MODE;
    }

    public String getPROP_TIME_UNIT() {
        return PROP_TIME_UNIT;
    }

}
