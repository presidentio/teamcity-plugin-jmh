package com.presidentio.teamcity.jmh.runner.common.cons;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by presidentio on 10/28/15.
 */
public class RunFromConst {

    public static final String MAVEN = "maven";
    public static final String GRADLE = "gradle";
    public static final String JAR = "jar";

    public static final List<String> RUN_FROMS = Arrays.asList(MAVEN, JAR);

    public static final Map<String, String> RUN_FROMS_WITH_DESCRIPTION = new LinkedHashMap<>();

    static {
        RUN_FROMS_WITH_DESCRIPTION.put(MAVEN, "maven");
        RUN_FROMS_WITH_DESCRIPTION.put(GRADLE, "gradle");
        RUN_FROMS_WITH_DESCRIPTION.put(JAR, "jar");
    }

    public String name(String mode) {
        return RUN_FROMS_WITH_DESCRIPTION.get(mode);
    }

    public String getMAVEN() {
        return MAVEN;
    }

    public String getJAR() {
        return JAR;
    }

    public String getGRADLE() {
        return GRADLE;
    }

    public List<String> getRUN_FROMS() {
        return RUN_FROMS;
    }

    public Map<String, String> getRUN_FROMS_WITH_DESCRIPTION() {
        return RUN_FROMS_WITH_DESCRIPTION;
    }
}
