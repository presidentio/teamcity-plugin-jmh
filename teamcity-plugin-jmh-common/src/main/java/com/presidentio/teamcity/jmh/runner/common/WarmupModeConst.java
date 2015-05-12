package com.presidentio.teamcity.jmh.runner.common;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by presidentio on 12.05.15.
 */
public class WarmupModeConst {

    public static final String INDI = "INDI";
    public static final String BULK = "BULK";
    public static final String BULK_INDI = "BULK_INDI";

    public static final List<String> ALL = Arrays.asList(INDI, BULK, BULK_INDI);

    public static final Map<String, String> ALL_WITH_DESCRIPTION = new HashMap<>();

    static {
        ALL_WITH_DESCRIPTION.put(INDI, "INDI");
        ALL_WITH_DESCRIPTION.put(BULK, "BULK");
        ALL_WITH_DESCRIPTION.put(BULK_INDI, "BULK-INDI");
    }

}
