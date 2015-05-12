package com.presidentio.teamcity.jmh.runner.common;

import java.util.*;

/**
 * Created by Vitaliy on 04.05.2015.
 */
public class TimeUnitConst {

    public static final String DEFAULT = "default";
    public static final String MINUTES = "m";
    public static final String SECONDS = "s";
    public static final String MILLISECONDS = "ms";
    public static final String MICROSECONDS = "us";
    public static final String NANOSECONDS = "ns";

    public static final List<String> TIME_UNITS = Arrays.asList(DEFAULT, MINUTES, SECONDS, MILLISECONDS,
            MICROSECONDS, NANOSECONDS);

    public static final Map<String, String> TIME_UNITS_WITH_DESCRIPTION = new LinkedHashMap<>();

    static {
        TIME_UNITS_WITH_DESCRIPTION.put(DEFAULT, "<Default>");
        TIME_UNITS_WITH_DESCRIPTION.put(NANOSECONDS, "Nanoseconds");
        TIME_UNITS_WITH_DESCRIPTION.put(MICROSECONDS, "Microseconds");
        TIME_UNITS_WITH_DESCRIPTION.put(MILLISECONDS, "Milliseconds");
        TIME_UNITS_WITH_DESCRIPTION.put(SECONDS, "Seconds");
        TIME_UNITS_WITH_DESCRIPTION.put(MINUTES, "Minutes");
    }

    public String getDEFAULT() {
        return DEFAULT;
    }

    public String getMINUTES() {
        return MINUTES;
    }

    public String getSECONDS() {
        return SECONDS;
    }

    public String getMILLISECONDS() {
        return MILLISECONDS;
    }

    public String getMICROSECONDS() {
        return MICROSECONDS;
    }

    public String getNANOSECONDS() {
        return NANOSECONDS;
    }
}
