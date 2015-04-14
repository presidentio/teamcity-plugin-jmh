package com.presidentio.teamcity.jmh.runner.agent;

import com.presidentio.teamcity.jmh.runner.common.JmhRunnerConst;
import jetbrains.buildServer.agent.AgentBuildRunnerInfo;
import jetbrains.buildServer.agent.BuildAgentConfiguration;
import jetbrains.buildServer.agent.runner.CommandLineBuildService;
import jetbrains.buildServer.agent.runner.CommandLineBuildServiceFactory;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Vitaliy on 14.04.2015.
 */
public class JmhCommandLineBuildServiceFactory implements CommandLineBuildServiceFactory {
    @NotNull
    public CommandLineBuildService createService() {
        return null;
    }

    @NotNull
    public AgentBuildRunnerInfo getBuildRunnerInfo() {
        return new AgentBuildRunnerInfo() {
            @NotNull
            public String getType() {
                return JmhRunnerConst.RUNNER_TYPE;
            }

            public boolean canRun(@NotNull final BuildAgentConfiguration buildAgentConfiguration) {
                return true;
            }
        };
    }
}
