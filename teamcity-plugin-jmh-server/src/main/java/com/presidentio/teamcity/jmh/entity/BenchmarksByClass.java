package com.presidentio.teamcity.jmh.entity;

/**
 * Created by presidentio on 06.05.15.
 */
public class BenchmarksByClass extends BaseBenchmarkGroup<BenchmarksByMethod> {

    private String scoreUnit;

    public BenchmarksByClass() {
    }

    public BenchmarksByClass(String scoreUnit) {
        this.scoreUnit = scoreUnit;
    }

    public void add(Benchmark benchmark) {
        if (scoreUnit == null) {
            scoreUnit = benchmark.getPrimaryMetric().getScoreUnit();
        }
        String className = extractClass(benchmark.getBenchmark());
        BenchmarksByMethod byMethod = get(className);
        if (byMethod == null) {
            byMethod = new BenchmarksByMethod(scoreUnit);
            put(className, byMethod);
        }
        byMethod.add(benchmark);
    }

    public String getScoreUnit() {
        return scoreUnit;
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
