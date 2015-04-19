function barChartInit(elId, data) {

    var margin = {top: 50, right: 20, bottom: 10, left: 465},
        width = 1800 - margin.left - margin.right,
        height = 500 - margin.top - margin.bottom;

    var y = d3.scale.ordinal()
        .rangeRoundBands([0, height], .3);

    var x = d3.scale.linear()/*
        .rangeRound([0, width])*/;

    var color = d3.scale.ordinal()
        .range(["#c7001e", "#f6a580", "#cccccc", "#92c6db", "#086fad", "#009999", "#00CC99", "#00CC66", "#00CC00", "#00FF00"]);

    var xAxis = d3.svg.axis()
        .scale(x)
        .orient("top")
        .tickFormat(function (d) {
            return d + " ms"
        });

    var yAxis = d3.svg.axis()
        .scale(y)
        .orient("left")

    var svg = d3.select(elId).append("svg")
        .attr("width", width + margin.left + margin.right)
        .attr("height", height + margin.top + margin.bottom)
        .attr("id", "d3-plot")
        .append("g")
        .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

    color.domain(["0.0", "50.0", "90.0", "95.0", "99.0", "99.9", "99.99", "99.999", "99.9999", "100.0"]);

    (function (data) {
        for (var benchmarkName in data) {
            d = data[benchmarkName];
            var xPrev = d["0.0"];
            d.boxes = color.domain().map(function (name) {
                var a = {
                    name: name,
                    x0: xPrev,
                    x1: d[name],
                    N: parseFloat(name),
                    n: d[name]
                };
                xPrev = d[name];
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
            .attr("class", "x axis")
            .call(xAxis);

        svg.append("g")
            .attr("class", "y axis")
            .call(yAxis)

        var vakken = svg.selectAll(".question")
            .data(d3.keys(data))
            .enter().append("g")
            .attr("class", "bar")
            .attr("transform", function (key) {
                return "translate(0," + y(key) + ")";
            });

        var bars = vakken.selectAll("rect")
            .data(function (d) {
                return data[d].boxes;
            })
            .enter().append("g").attr("class", "subbar");

        bars.append("rect")
            .attr("height", y.rangeBand())
            .attr("x", function (d) {
                return x(d.x0);
            })
            .attr("width", function (d) {
                return x(d.x1) - x(d.x0);
            })
            .style("fill", function (d) {
                return color(d.name);
            });

        bars.append("text")
            .attr("x", function (d) {
                return x(d.x0);
            })
            .attr("y", y.rangeBand() / 2)
            .attr("dy", "0.5em")
            .attr("dx", "0.5em")
            .style("font", "10px sans-serif")
            .style("text-anchor", "begin")
            .text(function (d) {
                return d.n !== 0 && (d.x1 - d.x0) > 3 ? d.n : ""
            });

        vakken.insert("rect", ":first-child")
            .attr("height", y.rangeBand())
            .attr("x", "1")
            .attr("width", width)
            .attr("fill-opacity", "0.5")
            .style("fill", "#F5F5F5")
            .attr("class", function (d, index) {
                return index % 2 == 0 ? "even" : "uneven";
            });

        svg.append("g")
            .attr("class", "y axis")
            .append("line")
            .attr("x1", x(0))
            .attr("x2", x(0))
            .attr("y2", height);

        var startp = svg.append("g").attr("class", "legendbox").attr("id", "mylegendbox");
        // this is not nice, we should calculate the bounding box and use that
        var legend_tabs = [0, 65, 130, 195, 260, 325, 390, 455, 520, 585];
        var legend = startp.selectAll(".legend")
            .data(color.domain().slice())
            .enter().append("g")
            .attr("class", "legend")
            .attr("transform", function (d, i) {
                return "translate(" + legend_tabs[i] + ",-45)";
            });

        legend.append("rect")
            .attr("x", 0)
            .attr("width", 18)
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
            .style("shape-rendering", "crispEdges")

        d3.selectAll(".axis line")
            .style("fill", "none")
            .style("stroke", "#000")
            .style("shape-rendering", "crispEdges")

        var movesize = width / 2 - startp.node().getBBox().width / 2;
        d3.selectAll(".legendbox").attr("transform", "translate(" + movesize + ",0)");


    })(data);
}