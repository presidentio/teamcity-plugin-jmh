/**
 * Copyright 2015 presidentio
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.presidentio.teamcity.jmh.runner.agent;

import com.presidentio.teamcity.jmh.runner.common.cons.PluginConst;
import com.presidentio.teamcity.jmh.runner.common.cons.SettingsConst;
import com.presidentio.teamcity.jmh.runner.common.param.RunnerParam;
import com.presidentio.teamcity.jmh.runner.common.param.RunnerParamProvider;
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
//    public static final String ARG_MODE = "-bm";
//    public static final String ARG_TIME_UNIT = "-tu";

    private ArtifactsWatcher artifactsWatcher;

    public JmhBuildServiceAdapter(ArtifactsWatcher artifactsWatcher) {
        this.artifactsWatcher = artifactsWatcher;
    }

    @NotNull
    @Override
    public ProgramCommandLine makeProgramCommandLine() throws RunBuildException {
        Map<String, String> runnerParameters = getRunnerParameters();
        List<String> arguments = new ArrayList<String>();
        System.out.println("arguments = " + arguments);
        String jarPath = runnerParameters.get(SettingsConst.PROP_JAR_PATH);
        arguments.add(ARG_JAR);
        arguments.add(jarPath);

        arguments.add(ARG_FORMAT);
        arguments.add(PluginConst.OUTPUT_FORMAT);

        arguments.add(ARG_OUTPUT_FILE);
        arguments.add(PluginConst.OUTPUT_FILE);

        RunnerParamProvider runnerParamProvider = new RunnerParamProvider();
        for (RunnerParam runnerParam : runnerParamProvider.all()) {
            if(runnerParameters.containsKey(runnerParam.getName())){
                arguments.add(runnerParam.getCommandLineName());
                arguments.add(runnerParameters.get(runnerParam.getName()));
            }
        }

        return new SimpleProgramCommandLine(getRunnerContext(), "java", arguments);
    }

    @Override
    public void afterProcessSuccessfullyFinished() throws RunBuildException {
        artifactsWatcher.addNewArtifactsPath(PluginConst.OUTPUT_FILE);
    }
}
