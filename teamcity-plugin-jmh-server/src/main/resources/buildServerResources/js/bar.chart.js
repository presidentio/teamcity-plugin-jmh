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
function BenchmarkChart() {

    var colors = ["#f6a580", "#cccccc", "#92c6db", "#086fad", "#009999", "#00CC99", "#00CC66", "#00CC00", "#00FF00"];
    var percentiles = ["50.0", "90.0", "95.0", "99.0", "99.9", "99.99", "99.999", "99.9999", "100.0"];
    var color = d3.scale.ordinal().range(colors);

    this.drawChart = function (elId, data, unit) {

        var nameSize = 150;
        var outerPadding = .3;
        var barHeight = 40;
        var margin = {top: 50, right: 30, bottom: 10, left: nameSize};
        var height = Object.keys(data).length * barHeight + barHeight * outerPadding,
            width = 0;

        var y = d3.scale.ordinal();

        var x = d3.scale.linear();

        var xAxis = d3.svg.axis()
            .orient("top")
            .tickFormat(function (d) {
                return (d.toFixed(10) * 1.0) + " " + unit;
            });

        var yAxis = d3.svg.axis()
            .scale(y)
            .orient("left");

        color.domain(percentiles);

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
            return d.boxes[d.boxes.length - 1].x1;
        });

        x.domain([min_val, max_val]).nice();
        y.domain(Object.keys(data));

        y.rangeRoundBands([0, height], outerPadding, outerPadding);
        yAxis.scale(y);

        var svgReal = d3.select(elId).append("svg");

        svgReal.append("g")
            .attr('transform', "translate(0, " + margin.top + ")")
            .attr("class", "bar-background")
            .selectAll(".bar-background")
            .data(d3.keys(data))
            .enter()
            .append("rect")
            .attr("class", function (d) {
                var curData = data[d];
                if (bChart.isFailed(curData)) {
                    return "failing";
                } else {
                    return "successful";
                }
            })
            .attr("dy", ".35em")
            .attr("height", y.rangeBand() + barHeight * 0.3)
            .attr("width", "100%")
            .attr("transform", function (key) {
                return "translate(0," + (y(key) - barHeight * 0.15) + ")";
            })
            .style("fill-opacity", "0.09");

        var svg = svgReal.append("g")
            .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

        svg.append("g")
            .attr("class", "x axis");

        svg.append("g")
            .attr("class", "y axis")
            .call(yAxis);

        svg.selectAll(".y.axis text")
            .style("font-size", function (d) {
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
        var barAvgText = barAvg.append("text")
            .style("font-size", "0.95em")
            .text(function (d) {
                return data[d].avg + " " + unit;
            });

        var barPrevAvg = vakken.append("g")
            .style("display", function (d) {
                var curData = data[d];
                if (curData.prevAvg != undefined) {
                    return "block";
                } else {
                    return "none";
                }
            })
            .attr("class", "bar-prev-avg");
        barPrevAvg.append("line")
            .attr("stroke-dasharray", "2,2")
            .attr("y1", 0)
            .attr("y2", y.rangeBand() + 6)
            .attr("stroke-width", 1)
            .attr("stroke", "gray")
            .attr("transform", "translate(0,-3)");
        var barPrevAvgText = barPrevAvg.append("text")
            .style("font-size", "0.8em")
            .attr("fill", "gray")
            .text(function (d) {
                var curData = data[d];
                return curData.prevAvg + " " + unit;
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
            x.rangeRound([0, width]).nice();
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
            barAvg.attr("transform", function (d) {
                return "translate(" + x(data[d].avg) + ",0)"
            });
            barPrevAvg.attr("transform", function (d) {
                var curData = data[d];
                if (curData.prevAvg != undefined) {
                    return "translate(" + Math.min(width + 2, Math.max(2, x(data[d].prevAvg))) + ",0)"
                } else {
                    return "";
                }
            });
            barAvgText.attr("transform", function (d) {
                var textWidth = this.getComputedTextLength();
                var curData = data[d];
                var xTranslate = 3;
                var yTranslate = y.rangeBand() / 2 + 10;
                if (x(curData.avg) < textWidth) {
                    xTranslate = 3;
                } else {
                    if (x(curData.avg) + textWidth > width || curData.prevAvg != undefined && curData.avg < curData.prevAvg) {
                        xTranslate = -xTranslate - textWidth;
                    }
                }
                return "translate(" + xTranslate + "," + yTranslate + ")";
            });
            barPrevAvgText.attr("transform", function (d) {
                var textWidth = this.getComputedTextLength();
                var curData = data[d];
                var xTranslate = 3;
                var yTranslate = y.rangeBand() / 2 - 3;
                if (x(curData.prevAvg) < textWidth) {
                    xTranslate = 3;
                } else {
                    if (x(curData.prevAvg) + textWidth > width || curData.prevAvg != undefined && curData.avg > curData.prevAvg) {
                        xTranslate = -xTranslate - textWidth;
                    }
                }
                return "translate(" + xTranslate + "," + yTranslate + ")";
            })
        }

        resize();

        d3.select(window).on('resize', resize);
    };

    this.drawLegend = function (elId) {
        var svgReal = d3.select(elId).append("svg")
            .attr("height", 18)
            .attr("width", 700);
        var svg = svgReal.append("g");
        var startp = svg.append("g").attr("class", "legendbox");
        var legend_tabs = [0, 65, 130, 195, 260, 325, 390, 455, 520];
        var legend = startp.selectAll(".legend")
            .data(color.domain().slice())
            .enter().append("g")
            .attr("class", "legend")
            .attr("transform", function (d, i) {
                return "translate(" + legend_tabs[i] + ",0)";
            });
        var avgLegend = startp.append("g")
            .attr("transform", "translate(" + 585 + ",0)");
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

        var prevLegend = startp.append("g")
            .attr("transform", "translate(" + 635 + ",0)");
        prevLegend
            .append("line")
            .attr("stroke-dasharray", "2,2")
            .attr("y1", 0)
            .attr("y2", 18)
            .attr("x1", 0)
            .attr("x2", 0)
            .attr("stroke-width", 1)
            .attr("stroke", "black");
        prevLegend
            .append("text")
            .attr("x", 5)
            .attr("y", 9)
            .attr("dy", ".35em")
            .style("text-anchor", "begin")
            .style("font", "10px sans-serif")
            .text("prev. avg.");

        legend.append("rect")
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
    };

    var modes = {
        "thrpt": {
            "order": "desc"
        },
        "avgt": {
            "order": "asc"
        },
        "sample": {
            "order": "asc"
        },
        "ss": {
            "order": "asc"
        }
    };

    this.isFailed = function (d) {
        if (d.prevAvg == undefined) {
            return false;
        }
        var more = d.avg - d.prevAvg > (d.avg + d.prevAvg) / 2 / 100;
        return more && modes[d.mode].order == "asc";
    };
}

bChart = new BenchmarkChart();