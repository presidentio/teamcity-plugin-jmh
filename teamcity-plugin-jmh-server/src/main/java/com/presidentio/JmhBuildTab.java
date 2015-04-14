package com.presidentio;

import jetbrains.buildServer.web.openapi.*;
import org.jetbrains.annotations.NotNull;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by Vitaliy on 09.04.2015.
 */
public class JmhBuildTab extends SimpleCustomTab {
    
    public JmhBuildTab(@NotNull PagePlaces pagePlaces,
                                @NotNull PluginDescriptor descriptor) {
        super(pagePlaces, PlaceId.BUILD_RESULTS_TAB, "jmh", descriptor.getPluginResourcesPath("jmh.jsp"), "Benchmark results");
        register();
    }

    @Override
    public boolean isAvailable(@NotNull HttpServletRequest request) {
        return super.isAvailable(request);
    }

    @Override
    public void fillModel(@NotNull Map<String, Object> model, @NotNull HttpServletRequest request) {
        // fill model
    }
}
