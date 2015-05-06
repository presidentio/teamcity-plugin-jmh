package com.presidentio.teamcity.jmh.entity;

/**
 * Created by presidentio on 06.05.15.
 */
public class BenchmarksByClass extends BaseBenchmarkGroup<BenchmarksByMethod> {

    public BenchmarksByClass() {
    }

    public BenchmarksByClass(String scoreUnit) {
        super(scoreUnit);
    }

    @Override
    protected void put(Benchmark benchmark) {
        String className = extractClass(benchmark.getBenchmark());
        BenchmarksByMethod byMethod = get(className);
        if (byMethod == null) {
            byMethod = new BenchmarksByMethod();
            put(className, byMethod);
        }
        byMethod.add(benchmark);
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
