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
public class BenchmarksByMethod extends BaseBenchmarkGroup<BenchmarksByParameter>{

    private String scoreUnit;

    public BenchmarksByMethod() {
    }

    public BenchmarksByMethod(String scoreUnit) {
        this.scoreUnit = scoreUnit;
    }

    public void add(Benchmark benchmark) {
        if (scoreUnit == null) {
            scoreUnit = benchmark.getPrimaryMetric().getScoreUnit();
        }
        String methodName = extractMethod(benchmark.getBenchmark());

        BenchmarksByParameter byParameter = get(methodName);
        if (byParameter == null) {
            byParameter = new BenchmarksByParameter(scoreUnit);
            put(methodName, byParameter);
        }
        byParameter.add(benchmark);
    }

    private String extractMethod(String classWithMethod) {
        int index = classWithMethod.lastIndexOf('.');
        return classWithMethod.substring(index + 1);
    }

    public String getScoreUnit() {
        return scoreUnit;
    }



    public boolean contains(String methodName, String parameters) {
        BenchmarksByParameter byParameter = get(methodName);
        return byParameter != null && byParameter.containsKey(parameters);
    }

}
