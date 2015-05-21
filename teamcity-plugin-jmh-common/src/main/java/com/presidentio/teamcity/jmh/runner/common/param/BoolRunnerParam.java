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

import com.presidentio.teamcity.jmh.runner.common.cons.RunnerParamTypeConst;

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
        super(ALLOWED_VALUES, RunnerParamTypeConst.BOOL, name, commandLineName, required, shortDescription, description);
    }
}
