<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bs" tagdir="/WEB-INF/tags" %>
<script src="http://cdnjs.cloudflare.com/ajax/libs/d3/3.4.13/d3.min.js"></script>
<link rel="stylesheet" href="${teamcityPluginResourcesPath}css/bar.chart.css">
<link rel="stylesheet" href="${teamcityPluginResourcesPath}css/jmh.css">
<script src="${teamcityPluginResourcesPath}js/bar.chart.js"></script>
<script src="${teamcityPluginResourcesPath}js/jmh.js"></script>
<jsp:useBean id="mode" class="com.presidentio.teamcity.jmh.runner.common.ModeConst"/>

<div class="benchmark-chart-legend"></div>
<c:forEach items="${benchmarks}" var="modeEntry" varStatus="modeGroupStatus">
    <div class="group-title<c:if test="${modeGroupStatus.first}"> first-group</c:if>">
        <div class="group-collapsible" data-target="mode-container-${modeGroupStatus.index}">
            <span class="handle handle_expanded group-button"></span>
            <span class="mode-name">${mode.name(modeEntry.key)}</span>
        </div>
        <span class="tabCounter benchmark-counter">${modeEntry.value.size()}</span>
    </div>
    <c:set var="prevModeGroup" value="${prevBenchmarks.get(modeEntry.key)}"/>
    <div id="mode-container-${modeGroupStatus.index}" class="mode-container">
        <c:forEach items="${modeEntry.value}" var="entry" varStatus="groupStatus">
            <c:set var="prevGroup" value="${prevModeGroup.get(entry.key)}"/>
            <div class="group-title<c:if test="${groupStatus.first}"> first-group</c:if>">
                <div class="group-collapsible"
                     data-target="mod-${modeGroupStatus.index}-bar-chart-container-${groupStatus.index}">
                    <span class="handle handle_expanded group-button"></span>
                    <span class="group-name">${entry.key}</span>
                </div>
                <span class="benchmark-interval">${entry.value.minTime} ${entry.value.scoreUnit} - ${entry.value.maxTime} ${entry.value.scoreUnit}</span>
                <span class="tabCounter benchmark-counter">${entry.value.size()}</span>
            </div>
            <div id="mod-${modeGroupStatus.index}-bar-chart-container-${groupStatus.index}" class="bar-chart-container">
                <div class="chart-separator"></div>
                <div id="mod-${modeGroupStatus.index}-bar-chart-${groupStatus.index}"
                     style="margin-bottom: 50px;"></div>
                <script>
                    bChart.drawChart("#mod-${modeGroupStatus.index}-bar-chart-${groupStatus.index}", {
                        <c:forEach items="${entry.value}" var="benchmarkEntry" varStatus="benchmarkStatus">
                        <c:set var="prevBenchmark" value="${null}"/>
                        <c:if test="${prevGroup != null}">
                        <c:set var="prevBenchmark" value="${prevGroup.get(benchmarkEntry.key)}"/>
                        </c:if>
                        "${benchmarkEntry.key}": {
                            "percentiles": {
                                <c:forEach var="percentile" items="${benchmarkEntry.value.primaryMetric.scorePercentiles}" varStatus="status">
                                "${percentile.key}":${percentile.value}<c:if test="${!status.last}">, </c:if>
                                </c:forEach>
                            }, "avg":${benchmarkEntry.value.primaryMetric.score},
                            "mode": "${benchmarkEntry.value.mode}"
                            <c:if test="${prevBenchmark != null}">
                            , "prevAvg":${prevBenchmark.primaryMetric.score}
                            </c:if>
                        }<c:if test="${!benchmarkStatus.last}">, </c:if>
                        </c:forEach>
                    }, "${entry.value.scoreUnit}");
                </script>
            </div>
        </c:forEach>
    </div>
</c:forEach>