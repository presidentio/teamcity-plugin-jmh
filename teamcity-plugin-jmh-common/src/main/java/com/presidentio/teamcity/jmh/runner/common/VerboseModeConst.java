package com.presidentio.teamcity.jmh.runner.common;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by presidentio on 12.05.15.
 */
public class VerboseModeConst {

    public static final String SILENT = "SILENT";
    public static final String NORMAL = "NORMAL";
    public static final String EXTRA = "EXTRA";

    public static final List<String> ALL = Arrays.asList(SILENT, NORMAL, EXTRA);

    public static final Map<String, String> ALL_WITH_DESCRIPTION = new HashMap<>();

    static {
        ALL_WITH_DESCRIPTION.put(SILENT, "Silent");
        ALL_WITH_DESCRIPTION.put(NORMAL, "Normal");
        ALL_WITH_DESCRIPTION.put(EXTRA, "Extra");
    }

}
