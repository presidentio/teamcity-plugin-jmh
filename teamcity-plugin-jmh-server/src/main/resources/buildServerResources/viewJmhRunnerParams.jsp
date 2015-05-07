<%@ taglib prefix="props" tagdir="/WEB-INF/tags/props" %>

<jsp:useBean id="settings" class="com.presidentio.teamcity.jmh.runner.common.SettingsConst"/>

<div class="parameter">
    Jar path: <strong><props:displayValue name="${settings.PROP_JAR_PATH}" emptyValue="not specified"/></strong>
</div>
<div class="parameter">
    Mode: <strong><props:displayValue name="${settings.PROP_MODE}" emptyValue="not specified"/></strong>
</div>
<div class="parameter">
    Benchmarks: <strong><props:displayValue name="${settings.PROP_BENCHMARKS}" emptyValue="not specified"/></strong>
</div>
<div class="parameter">
    Time unit: <strong><props:displayValue name="${settings.PROP_TIME_UNIT}" emptyValue="not specified"/></strong>
</div>
