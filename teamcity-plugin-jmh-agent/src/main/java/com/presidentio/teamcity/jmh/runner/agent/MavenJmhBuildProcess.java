package com.presidentio.teamcity.jmh.runner.agent;

import com.intellij.openapi.diagnostic.Logger;
import com.presidentio.teamcity.jmh.runner.common.cons.SettingsConst;
import jetbrains.buildServer.agent.BuildRunnerContext;
import jetbrains.buildServer.agent.artifacts.ArtifactsWatcher;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

/**
 * Created by presidentio on 10/30/15.
 */
public class MavenJmhBuildProcess extends JmhBuildProcess {

    private static final Logger LOGGER = Logger.getInstance("jmh-agent");

    public MavenJmhBuildProcess(ArtifactsWatcher artifactsWatcher, BuildRunnerContext buildRunnerContext) {
        super(new File(buildRunnerContext.getWorkingDirectory(),
                        getOrDefault(buildRunnerContext.getRunnerParameters(),
                                SettingsConst.PROP_MAVEN_MODULE_LOCATION, ".")),
                artifactsWatcher, buildRunnerContext);
    }

    @Override
    protected String buildClasspath() throws IOException, InterruptedException {
        Path tmpClasspathFile = Files.createTempFile("jmh-", ".classpath");
        List<String> command = Arrays.asList("mvn", "dependency:build-classpath",
                "-Dmdep.outputFile=" + tmpClasspathFile);
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.directory(getWorkingDir());
        getBuildRunnerContext().getBuild().getBuildLogger().message("Running command: " + command);
        LOGGER.info("Running command: " + command);
        Process process = processBuilder.start();
        new StreamToBuildLog(process.getInputStream(), getBuildRunnerContext().getBuild().getBuildLogger()).start();
        new StreamToBuildLog(process.getErrorStream(), getBuildRunnerContext().getBuild().getBuildLogger()).start();
        process.waitFor();
        String classpath = IOUtils.toString(new FileInputStream(tmpClasspathFile.toFile()));
        classpath += ":target/classes";
        tmpClasspathFile.toFile().delete();
        return classpath;
    }

}
