/**
 * Copyright 2015 presidentio
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.presidentio.teamcity.jmh.runner.common.param;

import com.presidentio.teamcity.jmh.runner.common.cons.Dictionary;
import com.presidentio.teamcity.jmh.runner.common.cons.RunnerParamTypeConst;

/**
 * Created by presidentio on 12.05.15.
 */
public class BaseRunnerParam implements RunnerParam {

    private String name;

    private String commandLineName;

    private boolean required;

    private String description;

    private String shortDescription;

    private int type;

    public BaseRunnerParam(int type, String name, String commandLineName, boolean required,
                           String shortDescription, String description) {
        this.type = type;
        this.name = name;
        this.commandLineName = commandLineName;
        this.required = required;
        this.description = description;
        this.shortDescription = shortDescription;
    }

    public BaseRunnerParam(String name, String commandLineName, boolean required,
                           String shortDescription, String description) {
        this(RunnerParamTypeConst.STRING, name, commandLineName, required, shortDescription, description);
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
    public String process(String value) throws ValidationException {
        if (required && (value == null || value.isEmpty())) {
            throw new ValidationException(Dictionary.ERROR_PARAMETER_IS_REQUIRED);
        }
        return value;
    }

    @Override
    public int getType() {
        return type;
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

    public void setType(int type) {
        this.type = type;
    }
}
