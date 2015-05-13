<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bs" tagdir="/WEB-INF/tags" %>

<script src="http://cdnjs.cloudflare.com/ajax/libs/d3/3.4.13/d3.min.js"></script>
<link rel="stylesheet" href="${teamcityPluginResourcesPath}css/bar.chart.css">
<link rel="stylesheet" href="${teamcityPluginResourcesPath}css/jmh.css">
<script src="${teamcityPluginResourcesPath}js/bar.chart.js"></script>
<script src="${teamcityPluginResourcesPath}js/jmh.js"></script>

<jsp:useBean id="mode" class="com.presidentio.teamcity.jmh.runner.common.cons.ModeConst"/>

<c:choose>
    <c:when test="${error != null}">
        ${error}
    </c:when>
    <c:otherwise>
        <div class="benchmark-chart-legend"></div>
        <c:forEach items="${benchmarks}" var="byModeEntry" varStatus="byModeStatus">
            <div class="group-title<c:if test="${byModeStatus.first}"> first-group</c:if>">
                <div class="group-collapsible" data-target="mode-container-${byModeStatus.index}">
                    <span class="handle handle_expanded group-button"></span>
                    <span class="mode-name">${mode.name(byModeEntry.key)}</span>
                </div>
                <span class="tabCounter benchmark-counter">${byModeEntry.value.size()}</span>
            </div>
            <c:set var="prevModeGroup" value="${prevBenchmarks.get(byModeEntry.key)}"/>
            <div id="mode-container-${byModeStatus.index}" class="mode-container">
                <c:forEach items="${byModeEntry.value}" var="byClassEntry" varStatus="byClassStatus">
                    <c:set var="prevGroup" value="${prevModeGroup.get(byClassEntry.key)}"/>
                    <div class="group-title<c:if test="${byClassStatus.first}"> first-group</c:if>">
                        <div class="group-collapsible"
                             data-target="mod-${byModeStatus.index}-bar-chart-container-${byClassStatus.index}">
                            <span class="handle handle_expanded group-button"></span>
                            <span class="group-name">${byClassEntry.key}</span>
                        </div>
                        <span class="benchmark-interval">${byClassEntry.value.minTime()} ${byClassEntry.value.scoreUnit} - ${byClassEntry.value.maxTime()} ${byClassEntry.value.scoreUnit}</span>
                        <span class="tabCounter benchmark-counter">${byClassEntry.value.size()}</span>
                    </div>
                    <div id="mod-${byModeStatus.index}-bar-chart-container-${byClassStatus.index}"
                         class="bar-chart-container">
                        <div class="chart-separator"></div>
                        <div id="mod-${byModeStatus.index}-bar-chart-${byClassStatus.index}"
                             style="margin-bottom: 50px;"></div>
                        <script>
                            bChart.drawChart("#mod-${byModeStatus.index}-bar-chart-${byClassStatus.index}", {
                                <c:forEach items="${byClassEntry.value}" var="byMethodEntry" varStatus="byMethodStatus">
                                <c:set var="prevBenchmark" value="${null}"/>
                                <c:if test="${prevGroup != null}">
                                <c:set var="prevBenchmark" value="${prevGroup.get(byMethodEntry.key)}"/>
                                </c:if>
                                "${byMethodEntry.key}": {
                                    "percentiles": {
                                        <c:forEach var="percentile" items="${byMethodEntry.value.primaryMetric.scorePercentiles}" varStatus="status">
                                        "${percentile.key}":${percentile.value}<c:if test="${!status.last}">, </c:if>
                                        </c:forEach>
                                    }, "avg":${byMethodEntry.value.primaryMetric.score},
                                    "mode": "${byMethodEntry.value.mode}"
                                    <c:if test="${prevBenchmark != null}">
                                    , "prevAvg":${prevBenchmark.primaryMetric.score}
                                    </c:if>
                                }<c:if test="${!byMethodStatus.last}">, </c:if>
                                </c:forEach>
                            }, "${byClassEntry.value.scoreUnit}");
                        </script>
                    </div>
                </c:forEach>
            </div>
        </c:forEach>
    </c:otherwise>
</c:choose>