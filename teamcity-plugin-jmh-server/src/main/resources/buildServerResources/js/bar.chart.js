function barChartInit(elId, data, unit) {

    var nameSize = 150;
    var margin = {top: 50, right: 20, bottom: 10, left: nameSize};
    var height = 0, width = 0;

    var y = d3.scale.ordinal();

    var x = d3.scale.linear();

    var xAxis = d3.svg.axis()
        .orient("top")
        .tickFormat(function (d) {
            return d + " " + unit;
        });

    var yAxis = d3.svg.axis()
        .scale(y)
        .orient("left");

    var color = d3.scale.ordinal()
        .range(["#c7001e", "#f6a580", "#cccccc", "#92c6db", "#086fad", "#009999", "#00CC99", "#00CC66", "#00CC00", "#00FF00"]);

    var svgReal = d3.select(elId).append("svg")
        .attr("id", "d3-plot");
    var svg = svgReal.append("g")
        .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

    color.domain(["0.0", "50.0", "90.0", "95.0", "99.0", "99.9", "99.99", "99.999", "99.9999", "100.0"]);

    for (var benchmarkName in data) {
        var d = data[benchmarkName];
        var xPrev = d.percentiles["0.0"];
        d.boxes = color.domain().map(function (name) {
            var a = {
                name: name,
                x0: xPrev,
                x1: d.percentiles[name],
                N: parseFloat(name),
                n: d.percentiles[name]
            };
            xPrev = d.percentiles[name];
            return a;
        });
    }

    var min_val = d3.min(d3.values(data), function (d) {
        return d.boxes[0].x0;
    });

    var max_val = d3.max(d3.values(data), function (d) {
        return d.boxes[9].x1;
    });

    x.domain([min_val, max_val]).nice();
    y.domain(Object.keys(data));

    svg.append("g")
        .attr("class", "x axis");
    height = Object.keys(data).length * (100 - margin.top - margin.bottom);

    y.rangeRoundBands([0, height], .3);
    yAxis.scale(y);

    svg.append("g")
        .attr("class", "y axis")
        .call(yAxis);

    svg.selectAll(".y.axis text")
        .style("font-size", function(d) {
            return Math.min(14, (nameSize - 8) / this.getComputedTextLength() * 24) + "px";
        });

    var vakken = svg.selectAll(".question")
        .data(d3.keys(data))
        .enter().append("g")
        .attr("class", "bar")
        .attr("transform", function (key) {
            return "translate(0," + y(key) + ")";
        })
        .attr("dy", ".35em");

    var bars = vakken.selectAll("rect")
        .data(function (d) {
            return data[d].boxes;
        })
        .enter().append("g").attr("class", "subbar");

    bars.append("rect")
        .attr("class", "bar-rect")
        .attr("height", y.rangeBand())
        .style("fill", function (d) {
            return color(d.name);
        });

    var barAvg = vakken.append("g")
        .attr("class", "bar-avg");
    barAvg.append("rect")
        .attr("width", 1)
        .attr("height", y.rangeBand() + 6)
        .style("fill", "#000")
        .attr("transform", "translate(0,-3)");
    barAvg.append("text")
        .text(function(d){return data[d].avg + " " + unit;})
        .attr("transform", "translate(3," + (y.rangeBand() / 2 + 3) + ")");

    vakken.insert("rect", ":first-child")
        .attr("height", y.rangeBand())
        .attr("x", "1")
        .attr("width", width)
        .attr("fill-opacity", "0.5")
        .style("fill", "#F5F5F5")
        .attr("class", function (d, index) {
            return index % 2 == 0 ? "even" : "uneven";
        });

    var startp = svg.append("g").attr("class", "legendbox");
    // this is not nice, we should calculate the bounding box and use that
    var legend_tabs = [0, 65, 130, 195, 260, 325, 390, 455, 520, 585];
    var legend_width = [18, 18, 18, 18, 18, 18, 18, 18, 18, 18];
    var legend = startp.selectAll(".legend")
        .data(color.domain().slice())
        .enter().append("g")
        .attr("class", "legend")
        .attr("transform", function (d, i) {
            return "translate(" + legend_tabs[i] + ",-45)";
        });
    var avgLegend = startp.append("g")
        .attr("transform", "translate(" + 650 + ",-45)");
    avgLegend
        .append("rect")
        .attr("width", 1)
        .attr("height", 18)
        .style("fill", "#000");
    avgLegend
        .append("text")
        .attr("x", 5)
        .attr("y", 9)
        .attr("dy", ".35em")
        .style("text-anchor", "begin")
        .style("font", "10px sans-serif")
        .text("avg");

    legend.append("rect")
        .attr("width", function (d, i) {
            return legend_width[i];
        })
        .attr("height", 18)
        .style("fill", color);

    legend.append("text")
        .attr("x", 22)
        .attr("y", 9)
        .attr("dy", ".35em")
        .style("text-anchor", "begin")
        .style("font", "10px sans-serif")
        .text(function (d) {
            return d;
        });

    d3.selectAll(".axis path")
        .style("fill", "none")
        .style("stroke", "#000")
        .style("shape-rendering", "crispEdges");

    d3.selectAll(".axis line")
        .style("fill", "none")
        .style("stroke", "#000")
        .style("shape-rendering", "crispEdges");


    function resize() {
        width = parseInt(d3.select(elId).node().getBoundingClientRect().width) - margin.left - margin.right;
        x.rangeRound([0, width]);
        xAxis.scale(x);
        svgReal.attr("width", width + margin.left + margin.right)
            .attr("height", height + margin.top + margin.bottom);
        svg.select(".x.axis")
            .call(xAxis);
        bars.select(".bar-rect")
            .attr("x", function (d) {
                return x(d.x0);
            })
            .attr("width", function (d) {
                return x(d.x1) - x(d.x0);
            });
        vakken.select(".bar-avg")
            .attr("transform", function (d) {
                return "translate(" + x(data[d].avg) + ",0)"
            });
        var movesize = width / 2 - startp.node().getBBox().width / 2;
        svgReal.selectAll(".legendbox").attr("transform", "translate(" + movesize + ",0)");
    }

    resize();

    d3.select(window).on('resize', resize);
}