package com.presidentio.teamcity.jmh.runner.common;

/**
 * Created by Vitaliy on 14.04.2015.
 */
public class JmhRunnerConst {
    
    public static final String RUNNER_TYPE = "jmh-runner";
    
    public static final String PROP_JAR_PATH = "jar_path";
    public static final String PROP_BENCHMARKS = "benchmarks";

    public static final String OUTPUT_FILE = "benchmarks.json";

    public String getRUNNER_TYPE() {
        return RUNNER_TYPE;
    }

    public String getPROP_JAR_PATH() {
        return PROP_JAR_PATH;
    }

    public String getOUTPUT_FILE() {
        return OUTPUT_FILE;
    }

    public String getPROP_BENCHMARKS() {
        return PROP_BENCHMARKS;
    }
}
