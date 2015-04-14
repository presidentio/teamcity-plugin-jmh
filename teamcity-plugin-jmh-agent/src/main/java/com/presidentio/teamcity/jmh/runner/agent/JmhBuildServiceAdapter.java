package com.presidentio.teamcity.jmh.runner.agent;

import com.presidentio.teamcity.jmh.runner.common.JmhRunnerConst;
import jetbrains.buildServer.RunBuildException;
import jetbrains.buildServer.agent.runner.BuildServiceAdapter;
import jetbrains.buildServer.agent.runner.ProgramCommandLine;
import jetbrains.buildServer.agent.runner.SimpleProgramCommandLine;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Map;

/**
 * Created by Vitaliy on 14.04.2015.
 */
public class JmhBuildServiceAdapter extends BuildServiceAdapter {

    @NotNull
    @Override
    public ProgramCommandLine makeProgramCommandLine() throws RunBuildException {
        Map<String, String> runnerParameters = getRunnerParameters();
        String jarPath = runnerParameters.get(JmhRunnerConst.PROP_JAR_PATH);
        return new SimpleProgramCommandLine(getRunnerContext(), "java", Arrays.asList("-jar", jarPath));
    }

}
