package com.presidentio.teamcity.jmh.runner.common;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by presidentio on 23.04.15.
 */
public class ModeConst {

    public static final String THROUGHPUT = "thrpt";
    public static final String AVERAGE_TIME = "avgt";
    public static final String SAMPLE_TIME = "sample";
    public static final String SINGLE_SHOT_TIME = "ss";
    public static final String ALL = "all";
    public static final String UNSPECIFIED = "unspecified";

    public static final Map<String, String> NAMES = new HashMap<>();

    static {
        NAMES.put(THROUGHPUT, "Throughput");
        NAMES.put(AVERAGE_TIME, "Average time");
        NAMES.put(SAMPLE_TIME, "Sample time");
        NAMES.put(SINGLE_SHOT_TIME, "Single shot time");
        NAMES.put(ALL, "All");
        NAMES.put(UNSPECIFIED, "Unspecified");
    }

    public String getTHROUGHPUT() {
        return THROUGHPUT;
    }

    public String getAVERAGE_TIME() {
        return AVERAGE_TIME;
    }

    public String getSAMPLE_TIME() {
        return SAMPLE_TIME;
    }

    public String getSINGLE_SHOT_TIME() {
        return SINGLE_SHOT_TIME;
    }

    public String getALL() {
        return ALL;
    }

    public String getUNSPECIFIED() {
        return UNSPECIFIED;
    }

    public String name(String mode) {
        return NAMES.get(mode);
    }
}
