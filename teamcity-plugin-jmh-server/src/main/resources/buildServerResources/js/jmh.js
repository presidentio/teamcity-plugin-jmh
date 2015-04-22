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
        }else{
            jQuery(this).find(".group-button").addClass("handle_overview_failing")
        }
    });
});