package com.presidentio.teamcity.jmh.runner.common;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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

    public static final List<String> MODES = Arrays.asList(ALL, AVERAGE_TIME, SAMPLE_TIME, SINGLE_SHOT_TIME,
            THROUGHPUT, UNSPECIFIED);

    public static final Map<String, String> MODES_WITH_DESCRIPTION = new HashMap<>();

    static {
        MODES_WITH_DESCRIPTION.put(ALL, "All");
        MODES_WITH_DESCRIPTION.put(AVERAGE_TIME, "Average time");
        MODES_WITH_DESCRIPTION.put(SAMPLE_TIME, "Sample time");
        MODES_WITH_DESCRIPTION.put(SINGLE_SHOT_TIME, "Single shot time");
        MODES_WITH_DESCRIPTION.put(THROUGHPUT, "Throughput");
        MODES_WITH_DESCRIPTION.put(UNSPECIFIED, "Unspecified");
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
        return MODES_WITH_DESCRIPTION.get(mode);
    }
}
