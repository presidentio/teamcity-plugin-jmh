package com.presidentio.teamcity.jmh.view;

import com.presidentio.teamcity.jmh.runner.common.JmhRunnerConst;
import com.presidentio.teamcity.jmh.runner.server.JmhRunnerBundle;
import jetbrains.buildServer.serverSide.SBuild;
import jetbrains.buildServer.serverSide.SBuildServer;
import jetbrains.buildServer.web.openapi.PagePlaces;
import jetbrains.buildServer.web.openapi.PlaceId;
import jetbrains.buildServer.web.openapi.PluginDescriptor;
import jetbrains.buildServer.web.openapi.SimpleCustomTab;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Vitaliy on 09.04.2015.
 */
public class JmhBuildTab extends SimpleCustomTab {
    
    private SBuildServer buildServer;

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
        return super.isAvailable(request);
    }

    @Override
    public void fillModel(@NotNull Map<String, Object> model, @NotNull HttpServletRequest request) {
        long buildId = Long.valueOf(request.getParameter("buildId"));
        SBuild build = buildServer.findBuildInstanceById(buildId);
        File benchmarksOutFile = new File(build.getArtifactsDirectory(), JmhRunnerConst.OUTPUT_FILE);
        try {
            model.put("data", IOUtils.toString(new FileReader(benchmarksOutFile)) + benchmarksOutFile.getAbsolutePath());
//            model.put("data", new String(build.getFileContent("target/" + JmhRunnerConst.OUTPUT_FILE)));
        } catch (FileNotFoundException e) {
            model.put("data", e.getMessage() + benchmarksOutFile.getAbsolutePath());
            e.printStackTrace();
        } catch (IOException e) {
            model.put("data", e.getMessage() + benchmarksOutFile.getAbsolutePath());
            e.printStackTrace();
        }
    }
}
