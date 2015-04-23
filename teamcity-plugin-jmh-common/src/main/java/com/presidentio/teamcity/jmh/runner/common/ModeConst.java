package com.presidentio.teamcity.jmh.runner.common;

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
}
