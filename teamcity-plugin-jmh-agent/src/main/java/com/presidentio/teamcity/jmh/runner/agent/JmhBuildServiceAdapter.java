package com.presidentio.teamcity.jmh.runner.agent;

import com.presidentio.teamcity.jmh.runner.common.JmhRunnerConst;
import jetbrains.buildServer.RunBuildException;
import jetbrains.buildServer.agent.artifacts.ArtifactsWatcher;
import jetbrains.buildServer.agent.runner.BuildServiceAdapter;
import jetbrains.buildServer.agent.runner.ProgramCommandLine;
import jetbrains.buildServer.agent.runner.SimpleProgramCommandLine;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Vitaliy on 14.04.2015.
 */
public class JmhBuildServiceAdapter extends BuildServiceAdapter {
    
    private ArtifactsWatcher artifactsWatcher;

    public JmhBuildServiceAdapter(ArtifactsWatcher artifactsWatcher) {
        this.artifactsWatcher = artifactsWatcher;
    }

    @NotNull
    @Override
    public ProgramCommandLine makeProgramCommandLine() throws RunBuildException {
        Map<String, String> runnerParameters = getRunnerParameters();
        String jarPath = runnerParameters.get(JmhRunnerConst.PROP_JAR_PATH);
        String benchmarks = runnerParameters.get(JmhRunnerConst.PROP_BENCHMARKS);
        List<String> arguments = new ArrayList<String>();
        arguments.add("-jar");
        arguments.add(jarPath);
        arguments.add("-rf");
        arguments.add("json");
        arguments.add("-rff");
        arguments.add(JmhRunnerConst.OUTPUT_FILE);
        if (benchmarks != null && !benchmarks.isEmpty()) {
            arguments.add(benchmarks);
        }
        return new SimpleProgramCommandLine(getRunnerContext(), "java", arguments);
    }

    @Override
    public void afterProcessFinished() throws RunBuildException {
        artifactsWatcher.addNewArtifactsPath(JmhRunnerConst.OUTPUT_FILE);
    }
}
