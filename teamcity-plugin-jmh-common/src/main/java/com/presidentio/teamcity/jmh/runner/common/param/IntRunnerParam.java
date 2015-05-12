package com.presidentio.teamcity.jmh.runner.common.param;

import com.presidentio.teamcity.jmh.runner.common.Dictionary;

/**
 * Created by presidentio on 12.05.15.
 */
public class IntRunnerParam extends BaseRunnerParam {

    private Integer from;

    private Integer to;

    public IntRunnerParam(String name, String commandLineName, boolean required,
                          String shortDescription, String description, Integer from) {
        super(name, commandLineName, required, shortDescription, description);
        this.from = from;
    }

    public IntRunnerParam(String name, String commandLineName, boolean required,
                          String shortDescription, String description, Integer from, Integer to) {
        super(name, commandLineName, required,shortDescription, description);
        this.from = from;
        this.to = to;
    }

    public IntRunnerParam(String name, String commandLineName, boolean required,
                          String shortDescription, String description) {
        super(name, commandLineName, required, shortDescription, description);
    }

    @Override
    public String validate(String value) {
        try {
            int parsedValue = Integer.valueOf(value);
            if (from != null && parsedValue <= from) {
                return String.format(Dictionary.ERROR_NUMBER_MORE, from);
            }
            if (to != null && parsedValue <= to) {
                return String.format(Dictionary.ERROR_NUMBER_LESS, to);
            }
        } catch (NumberFormatException e) {
            return Dictionary.ERROR_NUMBER;
        }
        return super.validate(value);
    }
}
