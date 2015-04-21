package com.presidentio.teamcity.jmh.view;

import com.presidentio.teamcity.jmh.entity.Benchmark;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by presidentio on 21.04.15.
 */
public class GroupedBechmarks extends HashMap<String, Map<String, Benchmark>> {

    public GroupedBechmarks(List<Benchmark> benchmarks) {
        for (Benchmark benchmark : benchmarks) {
            int index = benchmark.getBenchmark().lastIndexOf('.');
            String className = benchmark.getBenchmark().substring(0, index);
            String methodName = benchmark.getBenchmark().substring(index + 1);
            Map<String, Benchmark> map = get(className);
            if (map == null) {
                map = new HashMap<String, Benchmark>();
                put(className, map);
            }
            map.put(methodName, benchmark);
        }
    }
}
