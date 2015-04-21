package com.presidentio.teamcity.jmh.view;

import com.intellij.openapi.diagnostic.Logger;
import com.presidentio.teamcity.jmh.entity.Benchmark;
import com.presidentio.teamcity.jmh.runner.common.JmhRunnerConst;
import com.presidentio.teamcity.jmh.runner.server.JmhRunnerBundle;
import jetbrains.buildServer.log.Loggers;
import jetbrains.buildServer.serverSide.SBuild;
import jetbrains.buildServer.serverSide.SBuildServer;
import jetbrains.buildServer.web.openapi.PagePlaces;
import jetbrains.buildServer.web.openapi.PlaceId;
import jetbrains.buildServer.web.openapi.PluginDescriptor;
import jetbrains.buildServer.web.openapi.SimpleCustomTab;
import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.jetbrains.annotations.NotNull;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Vitaliy on 09.04.2015.
 */
public class JmhBuildTab extends SimpleCustomTab {

    private static final Logger LOGGER = Loggers.SERVER;

    public static final String BUILD_ID = "buildId";

    private SBuildServer buildServer;
    private ObjectMapper objectMapper = new ObjectMapper();

    public JmhBuildTab(@NotNull SBuildServer buildServer,
                       @NotNull PagePlaces pagePlaces,
                       @NotNull PluginDescriptor descriptor) {
        super(pagePlaces, PlaceId.BUILD_RESULTS_TAB, JmhRunnerBundle.TAB_ID,
                descriptor.getPluginResourcesPath("tabJmhView.jsp"), JmhRunnerBundle.TAB_TITLE);
        register();
        this.buildServer = buildServer;
    }

    @Override
    public boolean isAvailable(@NotNull HttpServletRequest request) {
        long buildId = Long.valueOf(request.getParameter(BUILD_ID));
        SBuild build = buildServer.findBuildInstanceById(buildId);
        LOGGER.info("isAvailable=" + (build.isFinished() && getBenchmarks(build) != null));
        LOGGER.info("getBenchmarks=" + getBenchmarks(build));
        return build.isFinished() && getBenchmarks(build) != null;
    }

    @Override
    public void fillModel(@NotNull Map<String, Object> model, @NotNull HttpServletRequest request) {
        long buildId = Long.valueOf(request.getParameter(BUILD_ID));
        SBuild build = buildServer.findBuildInstanceById(buildId);
        model.put("benchmarks", getBenchmarks(build));
    }

    private GroupedBechmarks getBenchmarks(SBuild build) {
        File benchmarksFile = new File(build.getArtifactsDirectory(), JmhRunnerConst.OUTPUT_FILE);
        try {
            LOGGER.info("benchmarksFile=" + IOUtils.toString(new FileInputStream(benchmarksFile)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (benchmarksFile.exists()) {
            try {
                List<Benchmark> benchmarks = objectMapper.readValue(benchmarksFile,
                        objectMapper.getTypeFactory().constructCollectionType(List.class, Benchmark.class));
                return new GroupedBechmarks(benchmarks);
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (JsonParseException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
