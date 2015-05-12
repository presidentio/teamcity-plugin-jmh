package com.presidentio.teamcity.jmh.runner.server;

import com.presidentio.teamcity.jmh.runner.common.Dictionary;
import com.presidentio.teamcity.jmh.runner.common.SettingsConst;
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
/*

        String mode = map.get(SettingsConst.PROP_MODE);
        if (!ModeConst.MODES.contains(mode)) {
            result.add(new InvalidProperty(SettingsConst.PROP_MODE, Dictionary.ERROR_MODE_ILLEGAL));
        }

        String timeUnit = map.get(SettingsConst.PROP_TIME_UNIT);
        if (!TimeUnitConst.TIME_UNITS.contains(timeUnit)) {
            result.add(new InvalidProperty(SettingsConst.PROP_TIME_UNIT, Dictionary.ERROR_TIME_UNIT_ILLEGAL));
        }*/

        return result;
    }

}
