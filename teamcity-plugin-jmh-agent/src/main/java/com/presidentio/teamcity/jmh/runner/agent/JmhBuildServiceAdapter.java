package com.presidentio.teamcity.jmh.runner.agent;

import com.presidentio.teamcity.jmh.runner.common.JmhRunnerConst;
import com.presidentio.teamcity.jmh.runner.common.ModeConst;
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

    public static final String ARG_JAR = "-jar";
    public static final String ARG_FORMAT = "-rf";
    public static final String ARG_OUTPUT_FILE = "-rff";
    public static final String ARG_MODE = "-bm";

    private ArtifactsWatcher artifactsWatcher;

    public JmhBuildServiceAdapter(ArtifactsWatcher artifactsWatcher) {
        this.artifactsWatcher = artifactsWatcher;
    }

    @NotNull
    @Override
    public ProgramCommandLine makeProgramCommandLine() throws RunBuildException {
        Map<String, String> runnerParameters = getRunnerParameters();
        List<String> arguments = new ArrayList<String>();

        String jarPath = runnerParameters.get(JmhRunnerConst.PROP_JAR_PATH);
        arguments.add(ARG_JAR);
        arguments.add(jarPath);

        arguments.add(ARG_FORMAT);
        arguments.add(JmhRunnerConst.OUTPUT_FORMAT);

        arguments.add(ARG_OUTPUT_FILE);
        arguments.add(JmhRunnerConst.OUTPUT_FILE);

        String benchmarks = runnerParameters.get(JmhRunnerConst.PROP_BENCHMARKS);
        if (benchmarks != null && !benchmarks.isEmpty()) {
            arguments.add(benchmarks);
        }

        String mode = runnerParameters.get(JmhRunnerConst.PROP_MODE);
        if (!mode.equals(ModeConst.UNSPECIFIED)) {
            arguments.add(ARG_MODE);
            arguments.add(mode);
        }

        return new SimpleProgramCommandLine(getRunnerContext(), "java", arguments);
    }

    @Override
    public void afterProcessFinished() throws RunBuildException {
        artifactsWatcher.addNewArtifactsPath(JmhRunnerConst.OUTPUT_FILE);
    }
}
