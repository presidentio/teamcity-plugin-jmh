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
    } else {
        span.className = span.className.replace('handle_collapsed', 'handle_expanded');
        document.getElementById(groupTitle.getAttribute('data-target')).style.display = "block";
    }

}
jQuery(document).ready(function() {
    jQuery('.group-title').click(function() {
        toggleBenchmarkGroup(this);
    });
});