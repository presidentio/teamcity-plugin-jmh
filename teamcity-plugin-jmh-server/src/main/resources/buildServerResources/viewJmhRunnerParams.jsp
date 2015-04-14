<%@ taglib prefix="props" tagdir="/WEB-INF/tags/props" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="propertiesBean" scope="request" type="jetbrains.buildServer.controllers.BasePropertiesBean"/>
<jsp:useBean id="constants" class="com.presidentio.teamcity.jmh.runner.JmhRunnerConst"/>

<div class="parameter">
    Jmh Jar Path: <strong><props:displayValue name="${constants.PROP_JAR_PATH}" emptyValue="not specified"/></strong>
</div>
