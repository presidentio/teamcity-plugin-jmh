package com.presidentio.teamcity.jmh.runner.server;

import com.presidentio.teamcity.jmh.runner.common.JmhRunnerConst;
import com.presidentio.teamcity.jmh.runner.common.ModeConst;
import com.presidentio.teamcity.jmh.runner.common.TimeUnitConst;
import jetbrains.buildServer.serverSide.InvalidProperty;
import jetbrains.buildServer.serverSide.PropertiesProcessor;
import jetbrains.buildServer.util.PropertiesUtil;

import java.util.*;

/**
 * Created by Vitaliy on 14.04.2015.
 */
public class JmhRunTypePropertiesProcessor implements PropertiesProcessor {

    private static final List<String> MODES = Arrays.asList(ModeConst.ALL, ModeConst.AVERAGE_TIME,
            ModeConst.SAMPLE_TIME, ModeConst.SINGLE_SHOT_TIME, ModeConst.THROUGHPUT, ModeConst.UNSPECIFIED);

    private static final List<String> TIME_UNITS = Arrays.asList(TimeUnitConst.UNSPECIFIED, TimeUnitConst.MINUTES,
            TimeUnitConst.SECONDS, TimeUnitConst.MILLISECONDS, TimeUnitConst.MICROSECONDS, TimeUnitConst.NANOSECONDS);

    public Collection<InvalidProperty> process(Map<String, String> map) {
        List<InvalidProperty> result = new ArrayList<InvalidProperty>();

        String jarPath = map.get(JmhRunnerConst.PROP_JAR_PATH);
        if (PropertiesUtil.isEmptyOrNull(jarPath)) {
            result.add(new InvalidProperty(JmhRunnerConst.PROP_JAR_PATH, "Jar path must be specified"));
        }

        String mode = map.get(JmhRunnerConst.PROP_MODE);
        if (!MODES.contains(mode)) {
            result.add(new InvalidProperty(JmhRunnerConst.PROP_MODE, "Mode must be one of: " + MODES));
        }

        String timeUnit = map.get(JmhRunnerConst.PROP_TIME_UNIT);
        if (!TIME_UNITS.contains(timeUnit)) {
            result.add(new InvalidProperty(JmhRunnerConst.PROP_TIME_UNIT, "Time unit must be one of: " + TIME_UNITS));
        }

        return result;
    }

}
