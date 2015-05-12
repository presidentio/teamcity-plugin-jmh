<%@ taglib prefix="props" tagdir="/WEB-INF/tags/props" %>
<%@ taglib prefix="l" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="settings" class="com.presidentio.teamcity.jmh.runner.common.SettingsConst"/>
<jsp:useBean id="paramProvider" class="com.presidentio.teamcity.jmh.runner.common.param.RunnerParamProvider"/>
<jsp:useBean id="mode" class="com.presidentio.teamcity.jmh.runner.common.ModeConst"/>
<jsp:useBean id="timeUnit" class="com.presidentio.teamcity.jmh.runner.common.TimeUnitConst"/>
<jsp:useBean id="paramType" class="com.presidentio.teamcity.jmh.runner.common.ParamTypeConst"/>

<l:settingsGroup title="Jmh options">
    <c:forEach var="runnerParam" items="${paramProvider.all()}">
        <tr>
            <th><label for="${runnerParam.name}">${runnerParam.shortDescription}: </label></th>
            <td>
                <c:choose>
                    <c:when test="${runnerParam.type == paramType.STRING}">
                        <props:textProperty name="${runnerParam.name}" className="longField"/>
                    </c:when>
                    <c:when test="${runnerParam.type == paramType.BOOL}">
                        <props:selectProperty name="${runnerParam.name}" multiple="false">
                            <c:forEach var="option" items="${runnerParam.allowedValues}">
                                <props:option value="${option.key}">${option.value}</props:option>
                            </c:forEach>
                        </props:selectProperty>
                    </c:when>
                    <c:when test="${runnerParam.type == paramType.INT}">
                        <props:textProperty name="${runnerParam.name}" className="longField"/>
                    </c:when>
                    <c:when test="${runnerParam.type == paramType.STRING_SELECT}">
                        <props:selectProperty name="${runnerParam.name}" multiple="false">
                            <c:forEach var="option" items="${runnerParam.allowedValues}">
                                <props:option value="${option.key}">${option.value}</props:option>
                            </c:forEach>
                        </props:selectProperty>
                    </c:when>
                </c:choose>
                <span class="error" id="error_${runnerParam.name}"></span>
                <span class="smallNote">${runnerParam.description}</span>
            </td>
        </tr>
    </c:forEach>
    <%--<tr>
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
    <tr class="advancedSetting">
        <c:set value="${settings.PROP_BATCH_SIZE}" var="param"/>
        <th><label for="${param}">${paramProvider.get(param)}: </label></th>
        <td><props:textProperty name="${param}" className="longField"/>
            <span class="error" id="error_${param}"></span>
            <span class="smallNote">Benchmarks to run (regexp+).</span>
        </td>
    </tr>--%>
</l:settingsGroup>
