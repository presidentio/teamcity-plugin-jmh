package com.presidentio.teamcity.jmh.runner.common.param;

import com.presidentio.teamcity.jmh.runner.common.Dictionary;
import com.presidentio.teamcity.jmh.runner.common.ParamTypeConst;

import java.util.Map;

/**
 * Created by presidentio on 12.05.15.
 */
public class SelectRunnerParameter extends BaseRunnerParam {

    private Map<String, String> allowedValues;

    public SelectRunnerParameter(Map<String, String> allowedValues, int type, String name, String commandLineName,
                                 boolean required, String shortDescription, String description) {
        super(type, name, commandLineName, required, shortDescription, description);
        this.allowedValues = allowedValues;
    }

    public SelectRunnerParameter(Map<String, String> allowedValues, String name, String commandLineName,
                                 boolean required, String shortDescription, String description) {
        super(ParamTypeConst.STRING_SELECT, name, commandLineName, required, shortDescription, description);
        this.allowedValues = allowedValues;
    }

    @Override
    public String validate(String value) {
        if (!allowedValues.containsKey(value)) {
            return Dictionary.ERROR_MUST_BE_ONE_OF + allowedValues;
        }
        return super.validate(value);
    }

    public Map<String, String> getAllowedValues() {
        return allowedValues;
    }

    public void setAllowedValues(Map<String, String> allowedValues) {
        this.allowedValues = allowedValues;
    }
}
