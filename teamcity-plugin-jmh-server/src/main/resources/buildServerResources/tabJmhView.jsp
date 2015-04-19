<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bs" tagdir="/WEB-INF/tags" %>
<style type="text/css">
    .table-benchmark {
        border: 1px solid #DFDFDF;
        background-color: #F9F9F9;
        width: 100%;
        -moz-border-radius: 3px;
        -webkit-border-radius: 3px;
        border-radius: 3px;
        font-family: Arial, "Bitstream Vera Sans", Helvetica, Verdana, sans-serif;
        color: #333;
    }

    .table-benchmark td, .table-benchmark th {
        border-top-color: white;
        border-bottom: 1px solid #DFDFDF;
        color: #555;
    }

    .table-benchmark th {
        text-shadow: rgba(255, 255, 255, 0.796875) 0px 1px 0px;
        font-family: Georgia, "Times New Roman", "Bitstream Charter", Times, serif;
        font-weight: normal;
        padding: 7px 7px 8px;
        text-align: left;
        line-height: 1.3em;
        font-size: 14px;
    }

    .table-benchmark td {
        font-size: 12px;
        padding: 4px 7px 2px;
        vertical-align: top;
    }
</style>
<script src="http://cdnjs.cloudflare.com/ajax/libs/d3/3.4.13/d3.min.js"></script>
<script src="${teamcityPluginResourcesPath}js/bar.chart.js"></script>
<table class="table-benchmark">
    <tr>
        <th>Name</th>
        <th>Time</th>
    </tr>
    <c:forEach items="${benchmarks}" var="benchmark" varStatus="status">
        <tr>
            <td>${benchmark.benchmark}</td>
            <td>${benchmark.primaryMetric.score}</td>
        </tr>
        <tr>
            <td colspan="2">
                <div id="bar-chart-${status.index}" style="margin-bottom: 50px;"></div>
                <script>
                    barChartInit("#bar-chart-${status.index}", {
                        "${benchmark.benchmark}":{
                            <c:forEach var="percentile" items="${benchmark.primaryMetric.scorePercentiles}" varStatus="status">
                            "${percentile.key}":${percentile.value}<c:if test="${!status.last}">,</c:if>
                            </c:forEach>
                        }
                    });
                </script>
            </td>
        </tr>
    </c:forEach>
</table>
