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

    public boolean contains(String className, String methodName, String parameters) {
        BenchmarksByMethod byMethod = get(className);
        return byMethod != null && byMethod.contains(methodName, parameters);
    }
}
