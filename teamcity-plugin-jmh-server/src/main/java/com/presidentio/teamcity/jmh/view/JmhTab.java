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
import jetbrains.buildServer.serverSide.SBuild;
import jetbrains.buildServer.serverSide.SBuildServer;
import jetbrains.buildServer.serverSide.SFinishedBuild;
import jetbrains.buildServer.web.openapi.PagePlaces;
import jetbrains.buildServer.web.openapi.PlaceId;
import jetbrains.buildServer.web.openapi.PluginDescriptor;
import jetbrains.buildServer.web.openapi.SimpleCustomTab;
import org.codehaus.jackson.map.ObjectMapper;
import org.jetbrains.annotations.NotNull;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Vitaliy on 09.04.2015.
 */
public class JmhTab extends SimpleCustomTab {

    private static final Logger LOGGER = Loggers.SERVER;

    public static final String BUILD_ID = "buildId";

    private SBuildServer buildServer;
    private ObjectMapper objectMapper = new ObjectMapper();

    public JmhTab(@NotNull SBuildServer buildServer,
                  @NotNull PagePlaces pagePlaces,
                  @NotNull PluginDescriptor descriptor) {
        super(pagePlaces, PlaceId.BUILD_RESULTS_TAB, PluginConst.TAB_ID,
                descriptor.getPluginResourcesPath("tabJmh.jsp"), Dictionary.TAB_TITLE);
        register();
        this.buildServer = buildServer;
    }

    @Override
    public boolean isAvailable(@NotNull HttpServletRequest request) {
        long buildId;
        try {
            buildId = Long.valueOf(request.getParameter(BUILD_ID));
        } catch (NumberFormatException e) {
            LOGGER.warn("Illegal build id, must be a number: " + request.getParameter(BUILD_ID));
            return false;
        }
        SBuild build = buildServer.findBuildInstanceById(buildId);
        return hasBenchmarks(build);
    }

    @Override
    public void fillModel(@NotNull Map<String, Object> model, @NotNull HttpServletRequest request) {
        long buildId;
        try {
            buildId = Long.valueOf(request.getParameter(BUILD_ID));
        } catch (NumberFormatException e) {
            LOGGER.warn("Illegal build id, must be a number: " + request.getParameter(BUILD_ID));
            return;
        }
        SBuild build = buildServer.findBuildInstanceById(buildId);
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
        File benchmarksFile = new File(build.getArtifactsDirectory(), PluginConst.OUTPUT_FILE);
        return benchmarksFile.exists();
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
