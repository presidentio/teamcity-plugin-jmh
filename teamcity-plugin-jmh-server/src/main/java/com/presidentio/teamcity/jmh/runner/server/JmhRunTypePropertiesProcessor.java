package com.presidentio.teamcity.jmh.runner.server;

import com.presidentio.teamcity.jmh.runner.common.*;
import com.presidentio.teamcity.jmh.runner.common.Dictionary;
import jetbrains.buildServer.serverSide.InvalidProperty;
import jetbrains.buildServer.serverSide.PropertiesProcessor;
import jetbrains.buildServer.util.PropertiesUtil;

import java.util.*;

/**
 * Created by Vitaliy on 14.04.2015.
 */
public class JmhRunTypePropertiesProcessor implements PropertiesProcessor {

    public Collection<InvalidProperty> process(Map<String, String> map) {
        List<InvalidProperty> result = new ArrayList<InvalidProperty>();

        String jarPath = map.get(SettingsConst.PROP_JAR_PATH);
        if (PropertiesUtil.isEmptyOrNull(jarPath)) {
            result.add(new InvalidProperty(SettingsConst.PROP_JAR_PATH, Dictionary.ERROR_JAR_PATH_EMPTY));
        }

        String mode = map.get(SettingsConst.PROP_MODE);
        if (!ModeConst.MODES.contains(mode)) {
            result.add(new InvalidProperty(SettingsConst.PROP_MODE, Dictionary.ERROR_MODE_ILLEGAL));
        }

        String timeUnit = map.get(SettingsConst.PROP_TIME_UNIT);
        if (!TimeUnitConst.TIME_UNITS.contains(timeUnit)) {
            result.add(new InvalidProperty(SettingsConst.PROP_TIME_UNIT, Dictionary.ERROR_TIME_UNIT_ILLEGAL));
        }

        return result;
    }

}
