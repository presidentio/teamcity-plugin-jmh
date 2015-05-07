<%@ taglib prefix="props" tagdir="/WEB-INF/tags/props" %>
<%@ taglib prefix="l" tagdir="/WEB-INF/tags/layout" %>

<jsp:useBean id="settings" class="com.presidentio.teamcity.jmh.runner.common.SettingsConst"/>
<jsp:useBean id="mode" class="com.presidentio.teamcity.jmh.runner.common.ModeConst"/>
<jsp:useBean id="timeUnit" class="com.presidentio.teamcity.jmh.runner.common.TimeUnitConst"/>

<l:settingsGroup title="Jmh options">
    <tr>
        <th><label for="${settings.PROP_JAR_PATH}">Jmh Jar Path: </label></th>
        <td><props:textProperty name="${settings.PROP_JAR_PATH}" className="longField"/>
            <span class="error" id="error_${settings.PROP_JAR_PATH}"></span>
            <span class="smallNote">Jmh jar path.</span>
        </td>
    </tr>
    <tr>
        <th><label for="${settings.PROP_BENCHMARKS}">Benchmarks: </label></th>
        <td><props:textProperty name="${settings.PROP_BENCHMARKS}" className="longField"/>
            <span class="error" id="error_${settings.PROP_BENCHMARKS}"></span>
            <span class="smallNote">Benchmarks to run (regexp+).</span>
        </td>
    </tr>
    <tr class="advancedSetting">
        <th><label for="${settings.PROP_MODE}">Mode: </label></th>
        <td>
            <props:selectProperty name="${settings.PROP_MODE}" multiple="false">
                <props:option value="${mode.UNSPECIFIED}" selected="true">Unspecified</props:option>
                <props:option value="${mode.THROUGHPUT}">Throughput</props:option>
                <props:option value="${mode.AVERAGE_TIME}">AverageTime</props:option>
                <props:option value="${mode.SAMPLE_TIME}">SampleTime</props:option>
                <props:option value="${mode.SINGLE_SHOT_TIME}">SingleShotTime</props:option>
                <props:option value="${mode.ALL}">All</props:option>
            </props:selectProperty>
            <span class="error" id="error_${settings.PROP_MODE}"></span>
            <span class="smallNote">Benchmark mode.</span>
        </td>
    </tr>
    <tr class="advancedSetting">
        <th><label for="${settings.PROP_TIME_UNIT}">Time unit: </label></th>
        <td>
            <props:selectProperty name="${settings.PROP_TIME_UNIT}" multiple="false">
                <props:option value="${timeUnit.UNSPECIFIED}" selected="true">Unspecified</props:option>
                <props:option value="${timeUnit.MINUTES}">Minutes (m)</props:option>
                <props:option value="${timeUnit.SECONDS}">Seconds (s)</props:option>
                <props:option value="${timeUnit.MILLISECONDS}">Milliseconds (ms)</props:option>
                <props:option value="${timeUnit.MICROSECONDS}">Microseconds (us)</props:option>
                <props:option value="${timeUnit.NANOSECONDS}">Nanoseconds (ns)</props:option>
            </props:selectProperty>
            <span class="error" id="error_${settings.PROP_TIME_UNIT}"></span>
            <span class="smallNote">Output time unit.</span>
        </td>
    </tr>
</l:settingsGroup>
