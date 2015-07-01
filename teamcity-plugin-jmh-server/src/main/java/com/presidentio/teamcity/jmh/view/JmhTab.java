/**
 * Copyright 2015 presidentio
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.presidentio.teamcity.jmh.view;

import com.intellij.openapi.diagnostic.Logger;
import com.presidentio.teamcity.jmh.entity.*;
import com.presidentio.teamcity.jmh.runner.common.cons.Dictionary;
import com.presidentio.teamcity.jmh.runner.common.cons.PluginConst;
import com.presidentio.teamcity.jmh.runner.common.cons.UnitConverter;
import jetbrains.buildServer.log.Loggers;
import jetbrains.buildServer.serverSide.BuildsManager;
import jetbrains.buildServer.serverSide.SBuild;
import jetbrains.buildServer.serverSide.SBuildServer;
import jetbrains.buildServer.serverSide.SFinishedBuild;
import jetbrains.buildServer.serverSide.artifacts.BuildArtifactsViewMode;
import jetbrains.buildServer.web.openapi.BuildTab;
import jetbrains.buildServer.web.openapi.PluginDescriptor;
import jetbrains.buildServer.web.openapi.WebControllerManager;
import org.codehaus.jackson.map.ObjectMapper;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Vitaliy on 09.04.2015.
 */
public class JmhTab extends BuildTab {

    private static final Logger LOGGER = Loggers.SERVER;

    private SBuildServer buildServer;
    private ObjectMapper objectMapper = new ObjectMapper();

    public JmhTab(@NotNull SBuildServer buildServer,
                  @NotNull WebControllerManager webControllerManager,
                  @NotNull BuildsManager buildsManager,
                  @NotNull PluginDescriptor descriptor) {
        super(PluginConst.TAB_ID, Dictionary.TAB_TITLE, webControllerManager,
                buildsManager, descriptor.getPluginResourcesPath("tabJmh.jsp"));
        this.buildServer = buildServer;
    }

    @Override
    protected boolean isAvailableFor(@NotNull SBuild build) {
        return hasBenchmarks(build);
    }

    @Override
    protected void fillModel(@NotNull Map<String, Object> model, @NotNull SBuild build) {
        List<SFinishedBuild> buildsBefore = buildServer.getHistory().getEntriesBefore(build, true);
        try {
            BenchmarksByMode benchmarkContainer = parseBenchmarks(build);
            BenchmarksByMode prevBenchmarkContainer = getPreviousBenchmarks(buildsBefore,
                    benchmarkContainer);
            model.put("benchmarks", benchmarkContainer);
            model.put("prevBenchmarks", prevBenchmarkContainer);
        } catch (IOException e) {
            model.put("error", Dictionary.ERROR_FAILED_TO_PARSE_BENCHMARK);
            LOGGER.error(e);
        }

    }

    private Benchmark convertScoreUnit(Benchmark benchmark, String scoreUnit) {
        Benchmark result = new Benchmark(benchmark);
        PrimaryMetric primaryMetric = result.getPrimaryMetric();
        String unitFrom = primaryMetric.getScoreUnit();
        primaryMetric.setScore(UnitConverter.convert(primaryMetric.getScore(), unitFrom, scoreUnit));
        primaryMetric.setScoreError(UnitConverter.convert(primaryMetric.getScoreError(), unitFrom, scoreUnit));
        for (int i = 0; i < primaryMetric.getRawData().length; i++) {
            for (int j = 0; j < primaryMetric.getRawData()[i].length; j++) {
                primaryMetric.getRawData()[i][j] = UnitConverter.convert(primaryMetric.getRawData()[i][j], unitFrom, scoreUnit);
            }
        }
        for (int i = 0; i < primaryMetric.getScoreConfidence().length; i++) {
            primaryMetric.getScoreConfidence()[i] = UnitConverter.convert(primaryMetric.getScoreConfidence()[i], unitFrom, scoreUnit);
        }
        for (String key : primaryMetric.getScorePercentiles().keySet()) {
            primaryMetric.getScorePercentiles().put(key, UnitConverter.convert(primaryMetric.getScorePercentiles().get(key), unitFrom, scoreUnit));
        }
        return result;
    }

    private boolean hasBenchmarks(SBuild build) {
        return build.getArtifacts(BuildArtifactsViewMode.VIEW_ALL).findArtifact(PluginConst.OUTPUT_FILE).isAccessible();
    }

    private BenchmarksByMode parseBenchmarks(SBuild build) throws IOException {
        File benchmarksFile = new File(build.getArtifactsDirectory(), PluginConst.OUTPUT_FILE);
        List<Benchmark> benchmarks = objectMapper.readValue(benchmarksFile,
                objectMapper.getTypeFactory().constructCollectionType(List.class, Benchmark.class));
        BenchmarksByMode benchmarkContainer = new BenchmarksByMode();
        benchmarkContainer.addAll(benchmarks);
        return benchmarkContainer;
    }

    private BenchmarksByMode getPreviousBenchmarks(List<SFinishedBuild> buildsBefore,
                                                   BenchmarksByMode curByMode) {
        BenchmarksByMode prevByMode = new BenchmarksByMode();
        for (SFinishedBuild sFinishedBuild : buildsBefore) {
            try {
                if (hasBenchmarks(sFinishedBuild)) {
                    BenchmarksByMode byMode = parseBenchmarks(sFinishedBuild);
                    for (String mode : curByMode.keySet()) {
                        BenchmarksByClass curByClass = curByMode.get(mode);
                        if (byMode.containsKey(mode)) {
                            BenchmarksByClass byClass = byMode.get(mode);
                            for (String className : curByClass.keySet()) {
                                BenchmarksByMethod curByMethod = curByClass.get(className);
                                if (byClass.containsKey(className)) {
                                    BenchmarksByMethod byMethod = byClass.get(className);
                                    for (String methodName : curByMethod.keySet()) {
                                        if (byMethod.containsKey(methodName) && !prevByMode.contains(mode, className, methodName)) {
                                            Benchmark curBenchmark = curByMethod.get(methodName);
                                            Benchmark benchmark = byMethod.get(methodName);
                                            benchmark = convertScoreUnit(benchmark, curBenchmark.getPrimaryMetric().getScoreUnit());
                                            prevByMode.add(benchmark);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (IOException e) {
                LOGGER.error(e);
            }
        }
        return prevByMode;
    }

}
