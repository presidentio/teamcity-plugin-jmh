package com.presidentio.teamcity.jmh.runner.server;

import com.presidentio.teamcity.jmh.runner.common.JmhRunnerConst;
import com.presidentio.teamcity.jmh.runner.common.ModeConst;
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
        stringBuilder.append("Path to jmh jar: ").append(parameters.get(JmhRunnerConst.PROP_JAR_PATH));
        if (!parameters.get(JmhRunnerConst.PROP_MODE).equals(ModeConst.UNSPECIFIED)) {
            stringBuilder.append("\nJmh mode: ").append(parameters.get(JmhRunnerConst.PROP_JAR_PATH));
        }
        return stringBuilder.toString();
    }

    @NotNull
    @Override
    public String getType() {
        return JmhRunnerConst.RUNNER_TYPE;
    }

    @NotNull
    @Override
    public String getDisplayName() {
        return JmhRunnerBundle.DISPLAY_NAME;
    }

    @NotNull
    @Override
    public String getDescription() {
        return JmhRunnerBundle.DESCRIPTION;
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
        defaultProperties.put(JmhRunnerConst.PROP_JAR_PATH, "target/benchmarks.jar");
        return defaultProperties;
    }
}
