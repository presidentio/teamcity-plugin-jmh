/**
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
 */
function hasClass(element, cls) {
    return (' ' + element.className + ' ').indexOf(' ' + cls + ' ') > -1;
}
function toggleBenchmarkGroup(groupTitle) {
    var span;
    for (var i = 0; i < groupTitle.childNodes.length; i++) {
        if ((' ' + groupTitle.childNodes[i].className + ' ').indexOf(" handle ") > -1) {
            span = groupTitle.childNodes[i];
            break;
        }
    }
    if (hasClass(span, 'handle_expanded')) {
        span.className = span.className.replace('handle_expanded', 'handle_collapsed');
        document.getElementById(groupTitle.getAttribute('data-target')).style.display = "none";
        jQuery(groupTitle).parent().find(".benchmark-counter").css("display", "block");
    } else {
        span.className = span.className.replace('handle_collapsed', 'handle_expanded');
        document.getElementById(groupTitle.getAttribute('data-target')).style.display = "block";
        jQuery(groupTitle).parent().find(".benchmark-counter").css("display", "none");
    }
}

jQuery(document).ready(function () {
    bChart.drawLegend(".benchmark-chart-legend");
    jQuery(".group-collapsible").click(function () {
        toggleBenchmarkGroup(this);
    });
    jQuery(".group-collapsible").each(function () {
        var successful = jQuery("#" + jQuery(this).attr("data-target") + " .bar-background .failing").length == 0;
        if (successful) {
            jQuery(this).find(".group-button").addClass("handle_overview_successful")
        } else {
            jQuery(this).find(".group-button").addClass("handle_overview_failing")
        }
    });
});