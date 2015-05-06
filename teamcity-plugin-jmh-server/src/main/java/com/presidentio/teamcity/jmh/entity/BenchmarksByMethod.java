package com.presidentio.teamcity.jmh.entity;

/**
 * Created by presidentio on 06.05.15.
 */
public class BenchmarksByMethod extends BaseBenchmarkGroup<Benchmark>{

    @Override
    public void put(Benchmark benchmark) {
        String methodName = extractMethod(benchmark.getBenchmark());
        put(methodName, benchmark);
    }

    private String extractMethod(String classWithMethod) {
        int index = classWithMethod.lastIndexOf('.');
        return classWithMethod.substring(index + 1);
    }

}
