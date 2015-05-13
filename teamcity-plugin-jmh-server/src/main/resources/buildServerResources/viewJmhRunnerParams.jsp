<%@ taglib prefix="props" tagdir="/WEB-INF/tags/props" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="settings" class="com.presidentio.teamcity.jmh.runner.common.cons.SettingsConst"/>
<jsp:useBean id="paramProvider" class="com.presidentio.teamcity.jmh.runner.common.param.RunnerParamProvider"/>

<div class="parameter">
    Jar path: <strong><props:displayValue name="${settings.PROP_JAR_PATH}" emptyValue="not specified"/></strong>
</div>
<c:forEach var="runnerParam" items="${paramProvider.all()}">
    <div class="parameter">
            <c:out value="${runnerParam.shortDescription}"/>: <strong><props:displayValue name="${runnerParam.name}" emptyValue="not specified"/></strong>
    </div>
</c:forEach>
