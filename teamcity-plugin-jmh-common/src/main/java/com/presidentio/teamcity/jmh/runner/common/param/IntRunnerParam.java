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
public class IntRunnerParam extends BaseRunnerParam {

    private Integer from;

    private Integer to;

    public IntRunnerParam(String name, String commandLineName, boolean required,
                          String shortDescription, String description, Integer from) {
        super(RunnerParamTypeConst.INT, name, commandLineName, required, shortDescription, description);
        this.from = from;
    }

    public IntRunnerParam(String name, String commandLineName, boolean required,
                          String shortDescription, String description, Integer from, Integer to) {
        super(RunnerParamTypeConst.INT, name, commandLineName, required, shortDescription, description);
        this.from = from;
        this.to = to;
    }

    public IntRunnerParam(String name, String commandLineName, boolean required,
                          String shortDescription, String description) {
        super(RunnerParamTypeConst.INT, name, commandLineName, required, shortDescription, description);
    }

    @Override
    public String process(String value) throws ValidationException {
        if (value != null) {
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
        }
        return super.process(value);
    }
}
