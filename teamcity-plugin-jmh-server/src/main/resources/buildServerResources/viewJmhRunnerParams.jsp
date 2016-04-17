<%--
* Copyright 2015 presidentio
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*
--%>
<%@ taglib prefix="props" tagdir="/WEB-INF/tags/props" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="settings" class="com.presidentio.teamcity.jmh.runner.common.cons.SettingsConst"/>
<jsp:useBean id="paramProvider" class="com.presidentio.teamcity.jmh.runner.common.param.RunnerParamProvider"/>

<div class="parameter">
    Run from: <strong><props:displayValue name="${settings.PROP_RUN_FROM}" emptyValue="not specified"/></strong>
</div>
<div class="parameter">
    Maven module: <strong><props:displayValue name="${settings.PROP_MAVEN_MODULE_LOCATION}" emptyValue="not specified"/></strong>
</div>
<div class="parameter">
    Jar path: <strong><props:displayValue name="${settings.PROP_JAR_PATH}" emptyValue="not specified"/></strong>
</div>
<c:forEach var="runnerParam" items="${paramProvider.all()}">
    <div class="parameter">
            <c:out value="${runnerParam.shortDescription}"/>: <strong><props:displayValue name="${runnerParam.name}" emptyValue="not specified"/></strong>
    </div>
</c:forEach>
