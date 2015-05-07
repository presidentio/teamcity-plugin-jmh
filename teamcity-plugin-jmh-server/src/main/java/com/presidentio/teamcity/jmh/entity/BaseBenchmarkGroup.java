package com.presidentio.teamcity.jmh.entity;

import com.presidentio.teamcity.jmh.runner.common.UnitConverter;

import java.util.HashMap;

/**
 * Created by Vitaliy on 06.05.2015.
 */
public abstract class BaseBenchmarkGroup<B extends BenchmarkGroup> extends HashMap<String, B> 
        implements BenchmarkGroup {

    @Override
    public double minTime() {
        double minValue = Double.POSITIVE_INFINITY;
        for (BenchmarkGroup childBenchmarkGroup : values()) {
            double newMin = childBenchmarkGroup.minTime();
            if (newMin < minValue) {
                minValue = newMin;
            }
        }
        return minValue;
    }

    @Override
    public double maxTime() {
        double maxValue = Double.NEGATIVE_INFINITY;
        for (BenchmarkGroup childBenchmarkGroup : values()) {
            double newMax = childBenchmarkGroup.maxTime();
            if (newMax > maxValue) {
                maxValue = newMax;
            }
        }
        return maxValue;
    }

    protected Benchmark changeScoreUnit(Benchmark benchmark, String scoreUnit) {
        Benchmark result = new Benchmark(benchmark);
        PrimaryMetric primaryMetric = result.getPrimaryMetric();
        String unitFrom = primaryMetric.getScoreUnit();
        primaryMetric.setScore(UnitConverter.convert(primaryMetric.getScore(), unitFrom, scoreUnit));
        primaryMetric.setScoreError(UnitConverter.convert(primaryMetric.getScoreError(), unitFrom, scoreUnit));
        for (int i = 0; i < primaryMetric.getRawData().length; i++) {
            for (int j = 0; j < primaryMetric.getRawData()[i].length; j++) {
                primaryMetric.getRawData()[i][j] = UnitConverter.convert(primaryMetric.getRawData()[i][j], unitFrom, scoreUnit);
            }
        }
        for (int i = 0; i < primaryMetric.getScoreConfidence().length; i++) {
            primaryMetric.getScoreConfidence()[i] = UnitConverter.convert(primaryMetric.getScoreConfidence()[i], unitFrom, scoreUnit);
        }
        for (String key : primaryMetric.getScorePercentiles().keySet()) {
            primaryMetric.getScorePercentiles().put(key, UnitConverter.convert(primaryMetric.getScorePercentiles().get(key), unitFrom, scoreUnit));
        }
        return result;
    }
    
}
