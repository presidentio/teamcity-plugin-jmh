package com.presidentio.teamcity.jmh.entity;



import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Argonaught on 19/06/2016.
 */
public class BenchmarkGroupTest {
    @Test
    public void testGroupingNoParams() throws IOException {
        List<Benchmark> benchmarks = BenchmarkUtil.parseBenchmarks(BenchmarkUtil.RESULT_NO_PARAMS);
        assertFalse( benchmarks.isEmpty() );


        BenchmarksByMode byMode = new BenchmarksByMode();
        for (Benchmark benchmark : benchmarks) {
            byMode.add(benchmark);
        }

        BenchmarksByClass byClass = byMode.get("avgt");
        assertNotNull( byClass );

        BenchmarksByMethod byMethod = byClass.get("com.argonaught.jmhtest.SomeBenchmark");
        assertNotNull(byMethod);

        BenchmarksByParameter byParameter = byMethod.get("bench");
        assertNotNull(byParameter);

        Benchmark benchmark = byParameter.get(BenchmarksByParameter.NO_PARAMS);
        assertNotNull(benchmark);

        benchmark = byParameter.get("arg: 1 certainty: 0 ");
        assertNull(benchmark);

    }

    @Test
    public void testGroupingWithParams() throws IOException {
        List<Benchmark> benchmarks = BenchmarkUtil.parseBenchmarks(BenchmarkUtil.RESULT_WITH_PARAMS);
        assertFalse( benchmarks.isEmpty() );


        BenchmarksByMode byMode = new BenchmarksByMode();
        for (Benchmark benchmark : benchmarks) {
            byMode.add(benchmark);
        }

        BenchmarksByClass byClass = byMode.get("avgt");
        assertNotNull( byClass );

        BenchmarksByMethod byMethod = byClass.get("com.argonaught.jmhtest.SomeBenchmark");
        assertNotNull(byMethod);

        BenchmarksByParameter byParameter = byMethod.get("bench");
        assertNotNull(byParameter);

        Benchmark benchmark = byParameter.get("arg: 1 certainty: 0 ");
        assertNotNull(benchmark);



        benchmark = byParameter.get(BenchmarksByParameter.NO_PARAMS);
        assertNull(benchmark);
    }
}
