package com.presidentio.teamcity.jmh.runner;

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
    public Collection<InvalidProperty> process(Map<String, String> map) {
        List<InvalidProperty> result = new ArrayList<InvalidProperty>();

        String jarPath = map.get(JmhRunnerConst.PROP_JAR_PATH);

        if (PropertiesUtil.isEmptyOrNull(jarPath)) {
            result.add(new InvalidProperty(JmhRunnerConst.PROP_JAR_PATH, "Jar path must be specified"));
        }

        return result;
    }
}
