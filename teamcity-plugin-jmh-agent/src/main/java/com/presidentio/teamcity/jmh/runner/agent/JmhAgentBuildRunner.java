package com.presidentio.teamcity.jmh.runner.agent;

import com.presidentio.teamcity.jmh.runner.common.cons.PluginConst;
import com.presidentio.teamcity.jmh.runner.common.cons.RunFromConst;
import com.presidentio.teamcity.jmh.runner.common.cons.SettingsConst;
import jetbrains.buildServer.RunBuildException;
import jetbrains.buildServer.agent.*;
import jetbrains.buildServer.agent.artifacts.ArtifactsWatcher;
import org.jetbrains.annotations.NotNull;

/**
 * Created by presidentio on 10/30/15.
 */
public class JmhAgentBuildRunner implements AgentBuildRunner {

    private ArtifactsWatcher artifactsWatcher;

    public JmhAgentBuildRunner(ArtifactsWatcher artifactsWatcher) {
        this.artifactsWatcher = artifactsWatcher;
    }

    @NotNull
    @Override
    public BuildProcess createBuildProcess(AgentRunningBuild agentRunningBuild,
                                           BuildRunnerContext buildRunnerContext) throws RunBuildException {
        switch (buildRunnerContext.getRunnerParameters().get(SettingsConst.PROP_RUN_FROM)){
            case RunFromConst.JAR:
                return new JarJmhBuildProcess(artifactsWatcher, buildRunnerContext);
            case RunFromConst.MAVEN:
                return new MavenJmhBuildProcess(artifactsWatcher, buildRunnerContext);
            case RunFromConst.GRADLE:
                return new GradleJmhBuildProcess(artifactsWatcher, buildRunnerContext);
            default:
                throw new RunBuildException("Unknown run from: " + buildRunnerContext.getRunnerParameters().get(SettingsConst.PROP_RUN_FROM));
        }
    }

    @NotNull
    @Override
    public AgentBuildRunnerInfo getRunnerInfo() {
        return new AgentBuildRunnerInfo() {
            @NotNull
            public String getType() {
                return PluginConst.RUNNER_TYPE;
            }

            public boolean canRun(@NotNull final BuildAgentConfiguration buildAgentConfiguration) {
                return true;
            }
        };
    }

}
