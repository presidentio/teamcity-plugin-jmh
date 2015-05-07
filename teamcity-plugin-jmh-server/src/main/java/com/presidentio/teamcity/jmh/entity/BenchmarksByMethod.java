package com.presidentio.teamcity.jmh.entity;

/**
 * Created by presidentio on 06.05.15.
 */
public class BenchmarksByMethod extends BaseBenchmarkGroup<Benchmark>{

    private String scoreUnit;

    public BenchmarksByMethod() {
    }

    public BenchmarksByMethod(String scoreUnit) {
        this.scoreUnit = scoreUnit;
    }

    public void add(Benchmark benchmark) {
        if (scoreUnit == null) {
            scoreUnit = benchmark.getPrimaryMetric().getScoreUnit();
        } else {
            benchmark = changeScoreUnit(benchmark, scoreUnit);
        }
        String methodName = extractMethod(benchmark.getBenchmark());
        put(methodName, benchmark);
    }

    private String extractMethod(String classWithMethod) {
        int index = classWithMethod.lastIndexOf('.');
        return classWithMethod.substring(index + 1);
    }

    public String getScoreUnit() {
        return scoreUnit;
    }

}
