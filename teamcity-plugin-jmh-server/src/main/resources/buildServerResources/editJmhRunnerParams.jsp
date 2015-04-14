<%@ taglib prefix="props" tagdir="/WEB-INF/tags/props" %>
<%@ taglib prefix="l" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="admin" tagdir="/WEB-INF/tags/admin" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bs" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="propertiesBean" scope="request" type="jetbrains.buildServer.controllers.BasePropertiesBean"/>
<jsp:useBean id="constants" class="com.presidentio.teamcity.jmh.runner.JmhRunnerConst"/>

<l:settingsGroup title="Jmh options">
    <tr>
        <th><label for="${constants.PROP_JAR_PATH}">Jmh Jar Path: </label></th>
        <td><props:textProperty name="${constants.PROP_JAR_PATH}" className="longField"/>
            <span class="error" id="error_${constants.PROP_JAR_PATH}"></span>
            <span class="smallNote">Jmh jar path</span>
        </td>
    </tr>
</l:settingsGroup>
