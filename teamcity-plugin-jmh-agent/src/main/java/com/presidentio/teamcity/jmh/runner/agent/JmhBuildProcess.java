package com.presidentio.teamcity.jmh.runner.agent;

import com.intellij.openapi.diagnostic.Logger;
import com.presidentio.teamcity.jmh.runner.common.cons.PluginConst;
import com.presidentio.teamcity.jmh.runner.common.param.RunnerParam;
import com.presidentio.teamcity.jmh.runner.common.param.RunnerParamProvider;
import jetbrains.buildServer.RunBuildException;
import jetbrains.buildServer.agent.BuildFinishedStatus;
import jetbrains.buildServer.agent.BuildProcess;
import jetbrains.buildServer.agent.BuildRunnerContext;
import jetbrains.buildServer.agent.artifacts.ArtifactsWatcher;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by presidentio on 10/30/15.
 */
public abstract class JmhBuildProcess implements BuildProcess, Runnable {

    private static final Logger LOGGER = Logger.getInstance("jmh-agent");

    public static final String ARG_FORMAT = "-rf";
    public static final String ARG_OUTPUT_FILE = "-rff";

    private Thread processThread;

    private File workingDir;
    private ArtifactsWatcher artifactsWatcher;
    private File benchmarksOutput;
    private BuildRunnerContext buildRunnerContext;

    private BuildFinishedStatus buildFinishedStatus = BuildFinishedStatus.FINISHED_SUCCESS;

    public JmhBuildProcess(File workingDir, ArtifactsWatcher artifactsWatcher, BuildRunnerContext buildRunnerContext) {
        this.workingDir = workingDir;
        this.artifactsWatcher = artifactsWatcher;
        this.buildRunnerContext = buildRunnerContext;
        benchmarksOutput = new File(workingDir, PluginConst.OUTPUT_FILE);
    }

    protected static String getOrDefault(Map<String, String> map, String key, String defaultValue){
        String result = map.get(key);
        return result == null ? defaultValue : result;
    }

    @Override
    public void start() throws RunBuildException {
        processThread = new Thread(this);
        processThread.start();
    }

    @Override
    public boolean isInterrupted() {
        return processThread.isInterrupted();
    }

    @Override
    public boolean isFinished() {
        return !processThread.isAlive();
    }

    @Override
    public void interrupt() {
        processThread.interrupt();
    }

    @NotNull
    @Override
    public BuildFinishedStatus waitFor() throws RunBuildException {
        try {
            processThread.join();
            afterProcessSuccessfullyFinished();
            return buildFinishedStatus;
        } catch (InterruptedException e) {
            buildRunnerContext.getBuild().getBuildLogger().exception(e);
            LOGGER.error("Failed on waiting process finished", e);
            return BuildFinishedStatus.INTERRUPTED;
        }
    }

    public void afterProcessSuccessfullyFinished() throws RunBuildException {
        artifactsWatcher.addNewArtifactsPath(benchmarksOutput.getAbsolutePath());
    }

    protected abstract String buildClasspath() throws IOException, InterruptedException;

    private List<String> buildJmhArgs() {
        List<String> arguments = new ArrayList<>();

        arguments.add(ARG_FORMAT);
        arguments.add(PluginConst.OUTPUT_FORMAT);

        arguments.add(ARG_OUTPUT_FILE);
        arguments.add(benchmarksOutput.getAbsolutePath());

        RunnerParamProvider runnerParamProvider = new RunnerParamProvider();
        for (RunnerParam runnerParam : runnerParamProvider.all()) {
            if (getBuildRunnerContext().getRunnerParameters().containsKey(runnerParam.getName())) {
                arguments.add(runnerParam.getCommandLineName());
                arguments.add(getBuildRunnerContext().getRunnerParameters().get(runnerParam.getName()));
            }
        }
        return arguments;
    }

    @Override
    public void run() {
        List<String> command = new ArrayList<>();
        command.add("java");
        command.add("-cp");
        try {
            command.add(buildClasspath());
        } catch (IOException e) {
            buildFinishedStatus = BuildFinishedStatus.FINISHED_FAILED;
            buildRunnerContext.getBuild().getBuildLogger().exception(e);
            LOGGER.error("Failed to build classpath", e);
            return;
        } catch (InterruptedException e) {
            buildFinishedStatus = BuildFinishedStatus.INTERRUPTED;
            buildRunnerContext.getBuild().getBuildLogger().exception(e);
            LOGGER.error("Build classpath was interrupted", e);
            return;
        }
        command.add("org.openjdk.jmh.Main");
        command.addAll(buildJmhArgs());
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.directory(getWorkingDir());
        buildRunnerContext.getBuild().getBuildLogger().message("Running command: " + command);
        LOGGER.info("Running command: " + command);
        try {
            Process process = processBuilder.start();
            new StreamToBuildLog(process.getInputStream(), getBuildRunnerContext().getBuild().getBuildLogger()).start();
            new StreamToBuildLog(process.getErrorStream(), getBuildRunnerContext().getBuild().getBuildLogger()).start();
            process.waitFor();
        } catch (IOException e) {
            buildFinishedStatus = BuildFinishedStatus.FINISHED_FAILED;
            buildRunnerContext.getBuild().getBuildLogger().exception(e);
            LOGGER.error("Failed to run benchmarks", e);
        } catch (InterruptedException e) {
            buildFinishedStatus = BuildFinishedStatus.INTERRUPTED;
            buildRunnerContext.getBuild().getBuildLogger().exception(e);
            LOGGER.error("Benchmarks were interrupted", e);
        }
    }

    public File getWorkingDir() {
        return workingDir;
    }

    public BuildRunnerContext getBuildRunnerContext() {
        return buildRunnerContext;
    }

}
