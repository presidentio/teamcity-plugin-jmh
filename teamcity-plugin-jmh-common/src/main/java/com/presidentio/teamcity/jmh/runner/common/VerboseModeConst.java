package com.presidentio.teamcity.jmh.runner.common;

import java.util.*;

/**
 * Created by presidentio on 12.05.15.
 */
public class VerboseModeConst {

    public static final String DEFAULT = "default";
    public static final String SILENT = "SILENT";
    public static final String NORMAL = "NORMAL";
    public static final String EXTRA = "EXTRA";

    public static final List<String> ALL = Arrays.asList(DEFAULT, SILENT, NORMAL, EXTRA);

    public static final Map<String, String> ALL_WITH_DESCRIPTION = new LinkedHashMap<>();

    static {
        ALL_WITH_DESCRIPTION.put(DEFAULT, "<Default>");
        ALL_WITH_DESCRIPTION.put(SILENT, "Silent");
        ALL_WITH_DESCRIPTION.put(NORMAL, "Normal");
        ALL_WITH_DESCRIPTION.put(EXTRA, "Extra");
    }

}
