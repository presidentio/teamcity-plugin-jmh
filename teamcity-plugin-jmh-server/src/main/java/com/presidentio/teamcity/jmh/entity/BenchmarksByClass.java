package com.presidentio.teamcity.jmh.entity;

import java.util.HashMap;

/**
 * Created by presidentio on 06.05.15.
 */
public class BenchmarksByClass extends HashMap<String, BenchmarksByMethod> {

    public void add(Benchmark benchmark) {
        String className = extractClass(benchmark.getBenchmark());
        BenchmarksByMethod byMethod = get(className);
        if (byMethod == null) {
            byMethod = new BenchmarksByMethod();
            put(className, byMethod);
        }
    }

    private String extractClass(String classWithMethod) {
        int index = classWithMethod.lastIndexOf('.');
        return classWithMethod.substring(0, index);
    }

    public boolean contains(String className, String methodName) {
        BenchmarksByMethod byMethod = get(className);
        return byMethod != null && byMethod.containsKey(methodName);
    }
}
