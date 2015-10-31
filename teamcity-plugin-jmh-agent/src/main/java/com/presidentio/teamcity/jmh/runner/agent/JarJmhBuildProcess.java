package com.presidentio.teamcity.jmh.runner.agent;

import com.presidentio.teamcity.jmh.runner.common.cons.SettingsConst;
import jetbrains.buildServer.agent.BuildRunnerContext;
import jetbrains.buildServer.agent.artifacts.ArtifactsWatcher;

import java.io.IOException;

/**
 * Created by presidentio on 10/30/15.
 */
public class JarJmhBuildProcess extends JmhBuildProcess {

    public JarJmhBuildProcess(ArtifactsWatcher artifactsWatcher, BuildRunnerContext buildRunnerContext) {
        super(buildRunnerContext.getWorkingDirectory(), artifactsWatcher, buildRunnerContext);
    }

    @Override
    protected String buildClasspath() throws IOException, InterruptedException {
        return getBuildRunnerContext().getRunnerParameters().get(SettingsConst.PROP_JAR_PATH);
    }

}
