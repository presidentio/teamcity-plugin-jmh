package com.presidentio.teamcity.jmh.entity;

import java.util.List;

/**
 * Created by presidentio on 06.05.15.
 */
public class BenchmarksByMode extends BaseBenchmarkGroup<BenchmarksByClass> {

    public void addAll(List<Benchmark> benchmarks) {
        for (Benchmark benchmark : benchmarks) {
            add(benchmark);
        }
    }

    @Override
    protected void put(Benchmark benchmark) {
        BenchmarksByClass byClass = get(benchmark.getMode());
        if (byClass == null) {
            byClass = new BenchmarksByClass();
            put(benchmark.getMode(), byClass);
        }
        byClass.add(benchmark);
    }

    public boolean contains(String mode, String className, String methodName) {
        BenchmarksByClass byClass = get(mode);
        return byClass != null && byClass.contains(className, methodName);
    }

}
