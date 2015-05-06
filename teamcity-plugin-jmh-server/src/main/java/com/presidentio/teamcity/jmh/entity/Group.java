package com.presidentio.teamcity.jmh.entity;

import java.util.HashMap;

/**
 * Created by Vitaliy on 22.04.2015.
 */
public class Group extends HashMap<String, Benchmark> {

    public String getScoreUnit() {
        for (Benchmark benchmark : values()) {
            return benchmark.getPrimaryMetric().getScoreUnit();
        }
        return "us/op";
    }

    public Double getMinTime() {
        Double minValue = Double.MAX_VALUE;
        for (Benchmark benchmark : values()) {
            for (Double[] doubles : benchmark.getPrimaryMetric().rawData) {
                for (Double value : doubles) {
                    if (minValue > value) {
                        minValue = value;
                    }
                }
            }
        }
        return minValue;
    }

    public Double getMaxTime() {
        Double maxValue = 0D;
        for (Benchmark benchmark : values()) {
            for (Double[] doubles : benchmark.getPrimaryMetric().rawData) {
                for (Double value : doubles) {
                    if (maxValue < value) {
                        maxValue = value;
                    }
                }
            }
        }
        return maxValue;
    }
}
