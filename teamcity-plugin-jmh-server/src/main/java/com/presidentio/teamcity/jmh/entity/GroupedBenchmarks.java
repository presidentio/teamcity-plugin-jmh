package com.presidentio.teamcity.jmh.entity;

import java.util.HashMap;
import java.util.List;

/**
 * Created by presidentio on 21.04.15.
 */
public class GroupedBenchmarks extends HashMap<String, Group> {

    private String scoreUnit;

    public GroupedBenchmarks() {
    }

    public GroupedBenchmarks(List<Benchmark> benchmarks) {
        for (Benchmark benchmark : benchmarks) {
            add(benchmark);
        }
    }

    public void add(Benchmark benchmark) {
        int index = benchmark.getBenchmark().lastIndexOf('.');
        String className = benchmark.getBenchmark().substring(0, index);
        String methodName = benchmark.getBenchmark().substring(index + 1);
        Group group = get(className);
        if (group == null) {
            group = new Group();
            put(className, group);
        }
        group.put(methodName, benchmark);
        scoreUnit = benchmark.getPrimaryMetric().scoreUnit;
    }

    public String getScoreUnit() {
        return scoreUnit;
    }
}
