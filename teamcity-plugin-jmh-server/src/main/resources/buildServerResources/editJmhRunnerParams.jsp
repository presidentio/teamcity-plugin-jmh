<%@ taglib prefix="props" tagdir="/WEB-INF/tags/props" %>
<%@ taglib prefix="l" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="admin" tagdir="/WEB-INF/tags/admin" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bs" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="forms" tagdir="/WEB-INF/tags/forms" %>

<jsp:useBean id="propertiesBean" scope="request" type="jetbrains.buildServer.controllers.BasePropertiesBean"/>
<jsp:useBean id="constants" class="com.presidentio.teamcity.jmh.runner.common.JmhRunnerConst"/>
<jsp:useBean id="mode" class="com.presidentio.teamcity.jmh.runner.common.ModeConst"/>
<jsp:useBean id="timeUnit" class="com.presidentio.teamcity.jmh.runner.common.TimeUnitConst"/>

<l:settingsGroup title="Jmh options">
    <tr>
        <th><label for="${constants.PROP_JAR_PATH}">Jmh Jar Path: </label></th>
        <td><props:textProperty name="${constants.PROP_JAR_PATH}" className="longField"/>
            <span class="error" id="error_${constants.PROP_JAR_PATH}"></span>
            <span class="smallNote">Jmh jar path.</span>
        </td>
    </tr>
    <tr>
        <th><label for="${constants.PROP_BENCHMARKS}">Benchmarks: </label></th>
        <td><props:textProperty name="${constants.PROP_BENCHMARKS}" className="longField"/>
            <span class="error" id="error_${constants.PROP_BENCHMARKS}"></span>
            <span class="smallNote">Benchmarks to run (regexp+).</span>
        </td>
    </tr>
    <tr class="advancedSetting">
        <th><label for="${constants.PROP_MODE}">Mode: </label></th>
        <td>
            <props:selectProperty name="${constants.PROP_MODE}" multiple="false">
                <props:option value="${mode.UNSPECIFIED}" selected="true">Unspecified</props:option>
                <props:option value="${mode.THROUGHPUT}">Throughput</props:option>
                <props:option value="${mode.AVERAGE_TIME}">AverageTime</props:option>
                <props:option value="${mode.SAMPLE_TIME}">SampleTime</props:option>
                <props:option value="${mode.SINGLE_SHOT_TIME}">SingleShotTime</props:option>
                <props:option value="${mode.ALL}">All</props:option>
            </props:selectProperty>
            <span class="error" id="error_${constants.PROP_MODE}"></span>
            <span class="smallNote">Benchmark mode.</span>
        </td>
    </tr>
    <tr class="advancedSetting">
        <th><label for="${constants.PROP_TIME_UNIT}">Time unit: </label></th>
        <td>
            <props:selectProperty name="${constants.PROP_TIME_UNIT}" multiple="false">
                <props:option value="${timeUnit.UNSPECIFIED}" selected="true">Unspecified</props:option>
                <props:option value="${timeUnit.MINUTES}">Minutes (m)</props:option>
                <props:option value="${timeUnit.SECONDS}">Seconds (s)</props:option>
                <props:option value="${timeUnit.MILLISECONDS}">Milliseconds (ms)</props:option>
                <props:option value="${timeUnit.MICROSECONDS}">Microseconds (us)</props:option>
                <props:option value="${timeUnit.NANOSECONDS}">Nanoseconds (ns)</props:option>
            </props:selectProperty>
            <span class="error" id="error_${constants.PROP_TIME_UNIT}"></span>
            <span class="smallNote">Output time unit.</span>
        </td>
    </tr>
</l:settingsGroup>
