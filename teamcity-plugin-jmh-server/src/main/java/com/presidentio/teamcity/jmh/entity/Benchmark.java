/**
 * Copyright 2015 presidentio
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.presidentio.teamcity.jmh.entity;

/**
 * Created by Vitaliy on 16.04.2015.
 */
public class Benchmark implements BenchmarkGroup {

    private String benchmark;
    private String mode;
    private Integer threads;
    private Integer forks;
    private Integer warmupIterations;
    private String warmupTime;
    private Integer measurementIterations;
    private String measurementTime;
    private PrimaryMetric primaryMetric;
    private SecondaryMetrics secondaryMetrics;

    public Benchmark(Benchmark benchmark) {
        this.benchmark = benchmark.getBenchmark();
        this.mode = benchmark.getMode();
        this.threads = benchmark.getThreads();
        this.forks = benchmark.getForks();
        this.warmupIterations = benchmark.getWarmupIterations();
        this.warmupTime = benchmark.getWarmupTime();
        this.measurementIterations = benchmark.getMeasurementIterations();
        this.measurementTime = benchmark.getMeasurementTime();
        this.primaryMetric = new PrimaryMetric(benchmark.getPrimaryMetric());
        this.secondaryMetrics = new SecondaryMetrics(benchmark.getSecondaryMetrics());
    }

    public Benchmark() {
    }

    public String getBenchmark() {
        return benchmark;
    }

    public void setBenchmark(String benchmark) {
        this.benchmark = benchmark;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public Integer getThreads() {
        return threads;
    }

    public void setThreads(Integer threads) {
        this.threads = threads;
    }

    public Integer getForks() {
        return forks;
    }

    public void setForks(Integer forks) {
        this.forks = forks;
    }

    public Integer getWarmupIterations() {
        return warmupIterations;
    }

    public void setWarmupIterations(Integer warmupIterations) {
        this.warmupIterations = warmupIterations;
    }

    public String getWarmupTime() {
        return warmupTime;
    }

    public void setWarmupTime(String warmupTime) {
        this.warmupTime = warmupTime;
    }

    public Integer getMeasurementIterations() {
        return measurementIterations;
    }

    public void setMeasurementIterations(Integer measurementIterations) {
        this.measurementIterations = measurementIterations;
    }

    public String getMeasurementTime() {
        return measurementTime;
    }

    public void setMeasurementTime(String measurementTime) {
        this.measurementTime = measurementTime;
    }

    public PrimaryMetric getPrimaryMetric() {
        return primaryMetric;
    }

    public void setPrimaryMetric(PrimaryMetric primaryMetric) {
        this.primaryMetric = primaryMetric;
    }

    public SecondaryMetrics getSecondaryMetrics() {
        return secondaryMetrics;
    }

    public void setSecondaryMetrics(SecondaryMetrics secondaryMetrics) {
        this.secondaryMetrics = secondaryMetrics;
    }

    @Override
    public double minTime() {
        Double minValue = Double.MAX_VALUE;
        for (Double[] doubles : getPrimaryMetric().rawData) {
            for (Double value : doubles) {
                if (minValue > value) {
                    minValue = value;
                }
            }
        }
        return minValue;
    }

    @Override
    public double maxTime() {
        Double maxValue = 0D;
        for (Double[] doubles : getPrimaryMetric().rawData) {
            for (Double value : doubles) {
                if (maxValue < value) {
                    maxValue = value;
                }
            }
        }
        return maxValue;
    }
}
