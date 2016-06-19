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

    public void add(Benchmark benchmark) {
        BenchmarksByClass byClass = get(benchmark.getMode());
        if (byClass == null) {
            byClass = new BenchmarksByClass();
            put(benchmark.getMode(), byClass);
        }
        byClass.add(benchmark);
    }

    public boolean contains(String mode, String className, String methodName, String parameters) {
        BenchmarksByClass byClass = get(mode);
        return byClass != null && byClass.contains(className, methodName, parameters);
    }

}
