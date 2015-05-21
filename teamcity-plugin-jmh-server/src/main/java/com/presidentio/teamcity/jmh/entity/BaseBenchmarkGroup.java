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

import com.presidentio.teamcity.jmh.runner.common.cons.UnitConverter;

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
