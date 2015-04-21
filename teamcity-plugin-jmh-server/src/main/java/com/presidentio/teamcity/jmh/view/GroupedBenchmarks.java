package com.presidentio.teamcity.jmh.view;

import com.presidentio.teamcity.jmh.entity.Benchmark;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by presidentio on 21.04.15.
 */
public class GroupedBenchmarks extends HashMap<String, Group> {
    
    private String scoreUnit;

    public GroupedBenchmarks(List<Benchmark> benchmarks) {
        for (Benchmark benchmark : benchmarks) {
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
    }
    
    public String getScoreUnit(){
        return scoreUnit;
    }
    
    public Double getMinTime(){
        Double minValue = Double.MAX_VALUE;
        for (Map<String,Benchmark> stringBenchmarkMap : values()) {
            for (Benchmark benchmark : stringBenchmarkMap.values()) {
                for (Double[] doubles : benchmark.getPrimaryMetric().rawData) {
                    for (Double value : doubles) {
                        if(minValue > value){
                            minValue = value;
                        }
                    }
                }
            }
        }
        return minValue;
    }

    public Double getMaxTime(){
        Double maxValue = 0D;
        for (Map<String,Benchmark> stringBenchmarkMap : values()) {
            for (Benchmark benchmark : stringBenchmarkMap.values()) {
                for (Double[] doubles : benchmark.getPrimaryMetric().rawData) {
                    for (Double value : doubles) {
                        if(maxValue < value){
                            maxValue = value;
                        }
                    }
                }
            }
        }
        return maxValue;
    }
}
