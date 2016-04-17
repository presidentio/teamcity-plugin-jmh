/**
 * Copyright 2015 presidentio
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.presidentio.teamcity.jmh.runner.server;

import com.presidentio.teamcity.jmh.runner.common.cons.*;
import jetbrains.buildServer.serverSide.PropertiesProcessor;
import jetbrains.buildServer.serverSide.RunType;
import jetbrains.buildServer.serverSide.RunTypeRegistry;
import jetbrains.buildServer.web.openapi.PluginDescriptor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vitaliy on 14.04.2015.
 */
public class JmhRunType extends RunType {

    private final PluginDescriptor pluginDescriptor;

    public JmhRunType(final RunTypeRegistry runTypeRegistry, final PluginDescriptor pluginDescriptor) {
        this.pluginDescriptor = pluginDescriptor;
        runTypeRegistry.registerRunType(this);
    }

    @NotNull
    @Override
    public String describeParameters(@NotNull Map<String, String> parameters) {
        StringBuilder stringBuilder = new StringBuilder();
        if (parameters.containsKey(SettingsConst.PROP_RUN_FROM)) {
            String runFrom = parameters.get(SettingsConst.PROP_RUN_FROM);
            if (runFrom.equals(RunFromConst.JAR)) {
                stringBuilder.append(Dictionary.PATH_TO_JMH_JAR + ": ").append(parameters.get(SettingsConst.PROP_JAR_PATH));
            }
            if (runFrom.equals(RunFromConst.MAVEN)) {
                stringBuilder.append(Dictionary.PROP_MAVEN_MODULE_LOCATION + ": ").append(parameters.get(SettingsConst.PROP_MAVEN_MODULE_LOCATION));
            }
            if (runFrom.equals(RunFromConst.GRADLE)) {
                stringBuilder.append(Dictionary.PROP_GRADLE_MODULE_LOCATION + ": ").append(parameters.get(SettingsConst.PROP_GRADLE_MODULE_LOCATION));
            }
        }
        if (parameters.containsKey(SettingsConst.PROP_MODE)
                && !parameters.get(SettingsConst.PROP_MODE).equals(ModeConst.DEFAULT)) {
            stringBuilder.append("\n" + Dictionary.MODE + ": ").append(parameters.get(SettingsConst.PROP_MODE));
        }
        return stringBuilder.toString();
    }

    @NotNull
    @Override
    public String getType() {
        return PluginConst.RUNNER_TYPE;
    }

    @NotNull
    @Override
    public String getDisplayName() {
        return Dictionary.DISPLAY_NAME;
    }

    @NotNull
    @Override
    public String getDescription() {
        return Dictionary.DESCRIPTION;
    }

    @Nullable
    @Override
    public PropertiesProcessor getRunnerPropertiesProcessor() {
        return new JmhRunTypePropertiesProcessor();
    }

    @Nullable
    @Override
    public String getEditRunnerParamsJspFilePath() {
        return pluginDescriptor.getPluginResourcesPath("editJmhRunnerParams.jsp");
    }

    @Nullable
    @Override
    public String getViewRunnerParamsJspFilePath() {
        return pluginDescriptor.getPluginResourcesPath("viewJmhRunnerParams.jsp");
    }

    @Nullable
    @Override
    public Map<String, String> getDefaultRunnerProperties() {
        Map<String, String> defaultProperties = new HashMap<String, String>(1);
        defaultProperties.put(SettingsConst.PROP_RUN_FROM, PluginConst.DEFAULT_JAR_PATH);
        defaultProperties.put(SettingsConst.PROP_JAR_PATH, PluginConst.DEFAULT_JAR_PATH);
        defaultProperties.put(SettingsConst.PROP_MODE, ModeConst.DEFAULT);
        return defaultProperties;
    }
}
