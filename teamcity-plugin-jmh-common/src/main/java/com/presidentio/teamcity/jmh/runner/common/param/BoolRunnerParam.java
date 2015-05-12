package com.presidentio.teamcity.jmh.runner.common.param;

import com.presidentio.teamcity.jmh.runner.common.ParamTypeConst;

import java.util.HashMap;

/**
 * Created by presidentio on 12.05.15.
 */
public class BoolRunnerParam extends SelectRunnerParameter {

    private static final HashMap<String, String> ALLOWED_VALUES = new HashMap<>();

    static {
        ALLOWED_VALUES.put(Boolean.FALSE.toString(), Boolean.FALSE.toString());
        ALLOWED_VALUES.put(Boolean.TRUE.toString(), Boolean.TRUE.toString());
    }

    public BoolRunnerParam(String name, String commandLineName, boolean required, String shortDescription,
                           String description) {
        super(ALLOWED_VALUES, ParamTypeConst.BOOL, name, commandLineName, required, shortDescription, description);
    }
}
