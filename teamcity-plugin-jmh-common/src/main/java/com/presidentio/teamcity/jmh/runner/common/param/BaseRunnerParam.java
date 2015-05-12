package com.presidentio.teamcity.jmh.runner.common.param;

import com.presidentio.teamcity.jmh.runner.common.Dictionary;

/**
 * Created by presidentio on 12.05.15.
 */
public class BaseRunnerParam implements RunnerParam {

    private String name;

    private String commandLineName;

    private boolean required;

    private String description;

    private String shortDescription;

    public BaseRunnerParam(String name, String commandLineName, boolean required,
                           String shortDescription, String description) {
        this.name = name;
        this.commandLineName = commandLineName;
        this.required = required;
        this.description = description;
        this.shortDescription = shortDescription;
    }

    @Override
    public boolean isRequired() {
        return required;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getCommandLineName() {
        return commandLineName;

    }

    @Override
    public String getShortDescription() {
        return shortDescription;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String validate(String value) {
        if (required && (value == null || value.isEmpty())) {
            return Dictionary.ERROR_PARAMETER_IS_REQUIRED;
        }
        return null;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCommandLineName(String commandLineName) {
        this.commandLineName = commandLineName;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }
}
