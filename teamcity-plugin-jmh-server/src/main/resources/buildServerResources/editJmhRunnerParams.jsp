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
<%@ taglib prefix="l" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="settings" class="com.presidentio.teamcity.jmh.runner.common.cons.SettingsConst"/>
<jsp:useBean id="runFrom" class="com.presidentio.teamcity.jmh.runner.common.cons.RunFromConst"/>
<jsp:useBean id="paramProvider" class="com.presidentio.teamcity.jmh.runner.common.param.RunnerParamProvider"/>
<jsp:useBean id="paramType" class="com.presidentio.teamcity.jmh.runner.common.cons.RunnerParamTypeConst"/>

<l:settingsGroup title="Jmh options">
    <tr>
        <th><label for="${settings.PROP_RUN_FROM}">Run from: <l:star/></label></th>
        <td>
            <props:selectProperty name="${settings.PROP_RUN_FROM}" id="${settings.PROP_RUN_FROM}" multiple="false"
                                  onchange="BS.JmhRun.updateRunFrom()">
                <c:forEach var="option" items="${runFrom.RUN_FROMS_WITH_DESCRIPTION}">
                    <props:option value="${option.key}"><c:out value="${option.value}"/></props:option>
                </c:forEach>
            </props:selectProperty>
            <span class="error" id="error_${settings.PROP_RUN_FROM}"></span>
            <span class="smallNote">Benchmarks location.</span>
        </td>
    </tr>
    <tr id="${settings.PROP_MAVEN_MODULE_LOCATION}">
        <th><label for="${settings.PROP_MAVEN_MODULE_LOCATION}">Maven module: </label></th>
        <td><props:textProperty name="${settings.PROP_MAVEN_MODULE_LOCATION}" className="longField"/>
            <span class="error" id="error_${settings.PROP_MAVEN_MODULE_LOCATION}"></span>
            <span class="smallNote">Maven module path.</span>
        </td>
    </tr>
    <tr id="${settings.PROP_GRADLE_MODULE_LOCATION}">
        <th><label for="${settings.PROP_GRADLE_MODULE_LOCATION}">Gradle module: </label></th>
        <td><props:textProperty name="${settings.PROP_GRADLE_MODULE_LOCATION}" className="longField"/>
            <span class="error" id="error_${settings.PROP_GRADLE_MODULE_LOCATION}"></span>
            <span class="smallNote">Gradle module path.</span>
        </td>
    </tr>
    <tr id="row-${settings.PROP_GRADLE_USE_WRAPPER}">
        <th><label for="${settings.PROP_GRADLE_USE_WRAPPER}">Gradle Wrapper: </label></th>
        <td><props:checkboxProperty name="${settings.PROP_GRADLE_USE_WRAPPER}" onclick="BS.JmhRun.updateGradleUseWrapper()"/>
            <span class="error" id="error_${settings.PROP_GRADLE_USE_WRAPPER}"></span>
        </td>
    </tr>
    <tr id="${settings.PROP_GRADLE_WRAPPER_PATH}">
        <th><label for="${settings.PROP_GRADLE_WRAPPER_PATH}">Path to Wrapper script: <l:star/></label></th>
        <td><props:textProperty name="${settings.PROP_GRADLE_WRAPPER_PATH}" className="longField"/>
            <span class="error" id="error_${settings.PROP_GRADLE_WRAPPER_PATH}"></span>
            <span class="smallNote">Optional path to the Gradle wrapper script, relative to the working directory</span>
        </td>
    </tr>
    <tr id="${settings.PROP_JAR_PATH}">
        <th><label for="${settings.PROP_JAR_PATH}">Jmh Jar Path: <l:star/></label></th>
        <td><props:textProperty name="${settings.PROP_JAR_PATH}" className="longField"/>
            <span class="error" id="error_${settings.PROP_JAR_PATH}"></span>
            <span class="smallNote">Jmh jar path.</span>
        </td>
    </tr>
    <c:forEach var="runnerParam" items="${paramProvider.all()}">
        <tr <c:if test="${!runnerParam.required}">class="advancedSetting"</c:if>>
            <th><label for="${runnerParam.name}"><c:out value="${runnerParam.shortDescription}"/>:
                <c:if test="${runnerParam.required}"><l:star/></c:if></label></th>
            <td>
                <c:choose>
                    <c:when test="${runnerParam.type == paramType.STRING}">
                        <props:textProperty name="${runnerParam.name}" className="longField"/>
                    </c:when>
                    <c:when test="${runnerParam.type == paramType.BOOL}">
                        <props:checkboxProperty name="${runnerParam.name}"/>
                    </c:when>
                    <c:when test="${runnerParam.type == paramType.INT}">
                        <props:textProperty name="${runnerParam.name}" className="longField"/>
                    </c:when>
                    <c:when test="${runnerParam.type == paramType.STRING_SELECT}">
                        <props:selectProperty name="${runnerParam.name}" multiple="false">
                            <c:forEach var="option" items="${runnerParam.allowedValues}">
                                <props:option value="${option.key}"><c:out value="${option.value}"/></props:option>
                            </c:forEach>
                        </props:selectProperty>
                    </c:when>
                </c:choose>
                <span class="error" id="error_${runnerParam.name}"></span>
                <span class="smallNote"><c:out value="${runnerParam.description}"/></span>
            </td>
        </tr>
    </c:forEach>
</l:settingsGroup>

<script type="text/javascript">
    BS.JmhRun = {
        updateRunFrom: function () {
            var val = $('${settings.PROP_RUN_FROM}').value;
            BS.JmhRun.hideAll();
            if (val == '${runFrom.MAVEN}') {
                BS.Util.show($('${settings.PROP_MAVEN_MODULE_LOCATION}'));
            }
            if (val == '${runFrom.JAR}') {
                BS.Util.show($('${settings.PROP_JAR_PATH}'));
            }
            if (val == '${runFrom.GRADLE}') {
                BS.Util.show($('${settings.PROP_GRADLE_MODULE_LOCATION}'));
                BS.Util.show($('row-${settings.PROP_GRADLE_USE_WRAPPER}'));
                BS.JmhRun.updateGradleUseWrapper();
            }
            BS.MultilineProperties.updateVisible();
        },
        updateGradleUseWrapper: function () {
            if ($('${settings.PROP_GRADLE_USE_WRAPPER}').checked) {
                BS.Util.show($('${settings.PROP_GRADLE_WRAPPER_PATH}'));
            } else {
                BS.Util.hide($('${settings.PROP_GRADLE_WRAPPER_PATH}'));
            }
        },
        hideAll: function () {
            BS.Util.hide($('${settings.PROP_JAR_PATH}'));
            BS.Util.hide($('${settings.PROP_MAVEN_MODULE_LOCATION}'));
            BS.Util.hide($('${settings.PROP_GRADLE_MODULE_LOCATION}'));
            BS.Util.hide($('row-${settings.PROP_GRADLE_USE_WRAPPER}'));
            BS.Util.hide($('${settings.PROP_GRADLE_WRAPPER_PATH}'));
        }
    };
    BS.JmhRun.updateRunFrom();
</script>
