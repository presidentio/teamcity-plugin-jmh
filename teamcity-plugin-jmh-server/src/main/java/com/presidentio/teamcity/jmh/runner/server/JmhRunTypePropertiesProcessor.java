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
package com.presidentio.teamcity.jmh.runner.server;

import com.presidentio.teamcity.jmh.runner.common.cons.Dictionary;
import com.presidentio.teamcity.jmh.runner.common.cons.SettingsConst;
import com.presidentio.teamcity.jmh.runner.common.param.RunnerParam;
import com.presidentio.teamcity.jmh.runner.common.param.RunnerParamProvider;
import com.presidentio.teamcity.jmh.runner.common.param.ValidationException;
import jetbrains.buildServer.log.Loggers;
import jetbrains.buildServer.serverSide.InvalidProperty;
import jetbrains.buildServer.serverSide.PropertiesProcessor;
import jetbrains.buildServer.util.PropertiesUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by Vitaliy on 14.04.2015.
 */
public class JmhRunTypePropertiesProcessor implements PropertiesProcessor {

    @Override
    public Collection<InvalidProperty> process(Map<String, String> map) {
        List<InvalidProperty> result = new ArrayList<InvalidProperty>();
        Loggers.SERVER.info(map.toString());
        String jarPath = map.get(SettingsConst.PROP_JAR_PATH);
        if (PropertiesUtil.isEmptyOrNull(jarPath)) {
            result.add(new InvalidProperty(SettingsConst.PROP_JAR_PATH, Dictionary.ERROR_JAR_PATH_EMPTY));
        }

        RunnerParamProvider runnerParamProvider = new RunnerParamProvider();
        for (RunnerParam runnerParam : runnerParamProvider.all()) {
            try {
                String value = runnerParam.process(map.get(runnerParam.getName()));
                if (value == null) {
                    map.remove(runnerParam.getName());
                } else {
                    map.put(runnerParam.getName(), value);
                }
            } catch (ValidationException e) {
                result.add(new InvalidProperty(runnerParam.getName(), e.getMessage()));
            }
        }

        return result;
    }

}
