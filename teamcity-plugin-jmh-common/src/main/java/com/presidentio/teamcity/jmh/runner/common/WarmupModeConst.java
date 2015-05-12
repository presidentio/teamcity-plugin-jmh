package com.presidentio.teamcity.jmh.runner.common;

import java.util.*;

/**
 * Created by presidentio on 12.05.15.
 */
public class WarmupModeConst {

    public static final String DEFAULT = "default";
    public static final String INDI = "INDI";
    public static final String BULK = "BULK";
    public static final String BULK_INDI = "BULK_INDI";

    public static final List<String> ALL = Arrays.asList(DEFAULT, INDI, BULK, BULK_INDI);

    public static final Map<String, String> ALL_WITH_DESCRIPTION = new LinkedHashMap<>();

    static {
        ALL_WITH_DESCRIPTION.put(DEFAULT, "<Default>");
        ALL_WITH_DESCRIPTION.put(INDI, "INDI");
        ALL_WITH_DESCRIPTION.put(BULK, "BULK");
        ALL_WITH_DESCRIPTION.put(BULK_INDI, "BULK_INDI");
    }

}
