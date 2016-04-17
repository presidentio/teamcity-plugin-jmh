package com.presidentio.teamcity.jmh.runner.agent;

import com.intellij.openapi.diagnostic.Logger;
import com.presidentio.teamcity.jmh.runner.common.cons.SettingsConst;
import jetbrains.buildServer.agent.BuildRunnerContext;
import jetbrains.buildServer.agent.artifacts.ArtifactsWatcher;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;

import java.io.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by presidentio on 10/30/15.
 */
public class GradleJmhBuildProcess extends JmhBuildProcess {

    private static final Logger LOGGER = Logger.getInstance("jmh-agent");

    private static final String JMH_PLUGIN_CLASSPATH = "build/libs/*";
    private static final String JAVA_LIBRARY_DISTRIBUTION_PLUGIN = "\napply plugin: 'java-library-distribution'";
    private static final String DIST_ARCHIVE_DIRECTORY = "build/distributions";
    private static final String BUILD_GRADLE = "build.gradle";
    private static final String BUILD_GRADLE_BACK = "build.gradle.back";
    private static final String CLASSPATH_TMP_DIR = "build/teamcity-plugin-jmh/classpath";


    public GradleJmhBuildProcess(ArtifactsWatcher artifactsWatcher, BuildRunnerContext buildRunnerContext) {
        super(new File(buildRunnerContext.getWorkingDirectory(),
                        getOrDefault(buildRunnerContext.getRunnerParameters(),
                                SettingsConst.PROP_GRADLE_MODULE_LOCATION, ".")),
                artifactsWatcher, buildRunnerContext);
    }

    @Override
    protected String buildClasspath() throws IOException, InterruptedException {
        String classpath = buildClasspathUsingJmgPlugin();
        if (classpath == null) {
            classpath = buildClasspathUsingDistTar();
        }
        return classpath;
    }

    private String getGradleCommand() {
        boolean useWrapper = Boolean.valueOf(
                getBuildRunnerContext().getRunnerParameters().get(SettingsConst.PROP_GRADLE_USE_WRAPPER));
        if (useWrapper) {
            return getBuildRunnerContext().getRunnerParameters().get(SettingsConst.PROP_GRADLE_WRAPPER_PATH);
        } else {
            return "gradle";
        }
    }

    private String buildClasspathUsingJmgPlugin() throws IOException, InterruptedException {
        List<String> command = Arrays.asList(getGradleCommand(), "clean", "jmhJar");
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.directory(getWorkingDir());
        getBuildRunnerContext().getBuild().getBuildLogger().message("Running command: " + command);
        LOGGER.info("Running command: " + command);
        Process process = processBuilder.start();
        new StreamToBuildLog(process.getInputStream(), getBuildRunnerContext().getBuild().getBuildLogger()).start();
        new StreamToBuildLog(process.getErrorStream(), getBuildRunnerContext().getBuild().getBuildLogger()).start();
        int jmhPluginResult = process.waitFor();
        if (jmhPluginResult > 0) {
            LOGGER.info("Jmh plugin does not works (assume it does not applied)");
            getBuildRunnerContext().getBuild().getBuildLogger()
                    .message("Jmh plugin does not works (assume it does not applied)");
            return null;
        } else {
            return JMH_PLUGIN_CLASSPATH;
        }
    }

    private String buildClasspathUsingDistTar() throws IOException, InterruptedException {
        FileUtils.copyFile(new File(getWorkingDir(), BUILD_GRADLE), new File(getWorkingDir(), BUILD_GRADLE_BACK));
        FileWriter fileWriter = new FileWriter(new File(getWorkingDir(), BUILD_GRADLE), true);
        fileWriter.write(JAVA_LIBRARY_DISTRIBUTION_PLUGIN);
        fileWriter.close();
        List<String> command = Arrays.asList(getGradleCommand(), "clean", "distTar");
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.directory(getWorkingDir());
        getBuildRunnerContext().getBuild().getBuildLogger().message("Running command: " + command);
        LOGGER.info("Running command: " + command);
        Process process = processBuilder.start();
        new StreamToBuildLog(process.getInputStream(), getBuildRunnerContext().getBuild().getBuildLogger()).start();
        new StreamToBuildLog(process.getErrorStream(), getBuildRunnerContext().getBuild().getBuildLogger()).start();
        int jmhPluginResult = process.waitFor();
        FileUtils.copyFile(new File(getWorkingDir(), BUILD_GRADLE_BACK), new File(getWorkingDir(), BUILD_GRADLE));
        if (jmhPluginResult > 0) {
            LOGGER.info("java-library-distribution plugin does not works");
            getBuildRunnerContext().getBuild().getBuildLogger()
                    .message("java-library-distribution plugin does not works");
            return null;
        } else {
            File[] tarCollection = new File(getWorkingDir(), DIST_ARCHIVE_DIRECTORY)
                    .listFiles((FileFilter) new WildcardFileFilter("*.tar"));
            for (File file : tarCollection) {
                try {
                    File untarDirectory = new File(getWorkingDir(), CLASSPATH_TMP_DIR);
                    unTar(file, untarDirectory);
                    return buildClasspathFromDirectory(untarDirectory);
                } catch (ArchiveException e) {
                    LOGGER.error("Failed to untar archive: " + file);
                    getBuildRunnerContext().getBuild().getBuildLogger()
                            .message("Failed to untar archive: " + file);
                }
            }
            throw new IOException("Can't find and untar tar archive in " + DIST_ARCHIVE_DIRECTORY);
        }
    }

    private String buildClasspathFromDirectory(File dir) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Iterator<File> fileIterator = FileUtils.iterateFiles(dir, new String[]{"jar"}, true);
             fileIterator.hasNext(); ) {
            File next = fileIterator.next();
            if (stringBuilder.length() > 0) {
                stringBuilder.append(File.pathSeparator);
            }
            stringBuilder.append(next.getAbsolutePath());
        }
        return stringBuilder.toString();
    }

    private static List<File> unTar(final File inputFile, final File outputDir) throws IOException, ArchiveException {

        LOGGER.info(String.format("Untaring %s to dir %s.", inputFile.getAbsolutePath(), outputDir.getAbsolutePath()));

        final List<File> untaredFiles = new LinkedList<>();
        final InputStream is = new FileInputStream(inputFile);
        final TarArchiveInputStream debInputStream = (TarArchiveInputStream) new ArchiveStreamFactory()
                .createArchiveInputStream("tar", is);
        TarArchiveEntry entry;
        while ((entry = (TarArchiveEntry) debInputStream.getNextEntry()) != null) {
            final File outputFile = new File(outputDir, entry.getName());
            if (entry.isDirectory()) {
                LOGGER.info(String.format("Attempting to write output directory %s.", outputFile.getAbsolutePath()));
                if (!outputFile.exists()) {
                    LOGGER.info(String.format("Attempting to create output directory %s.", outputFile.getAbsolutePath()));
                    if (!outputFile.mkdirs()) {
                        throw new IllegalStateException(String.format("Couldn't create directory %s.", outputFile.getAbsolutePath()));
                    }
                }
            } else {
                LOGGER.info(String.format("Creating output file %s.", outputFile.getAbsolutePath()));
                final OutputStream outputFileStream = new FileOutputStream(outputFile);
                IOUtils.copy(debInputStream, outputFileStream);
                outputFileStream.close();
            }
            untaredFiles.add(outputFile);
        }
        debInputStream.close();

        return untaredFiles;
    }

}
