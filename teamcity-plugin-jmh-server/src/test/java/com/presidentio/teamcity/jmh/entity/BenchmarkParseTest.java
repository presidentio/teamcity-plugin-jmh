package com.presidentio.teamcity.jmh.entity;



import org.junit.Test;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by Argonaught on 18/06/2016.
 */
public class BenchmarkParseTest {

    @Test
    public void testParseParams() throws IOException {
        List<Benchmark> benchmarks = BenchmarkUtil.parseBenchmarks(BenchmarkUtil.RESULT_WITH_PARAMS);
        // check we actually parsed something
        assertFalse(benchmarks.isEmpty());

        Benchmark benchmark = benchmarks.get(0);

        // check that we parsed the params in the first benchmark
        assertEquals("1", benchmark.getParams().get("arg"));
        assertEquals("0", benchmark.getParams().get("certainty"));

        // test copy constructor
        Benchmark cloneBenchmark = new Benchmark(benchmark);

        // check that we parsed the params in the cloned benchmark
        assertEquals("1", cloneBenchmark.getParams().get("arg"));
        assertEquals("0", cloneBenchmark.getParams().get("certainty"));

    }

    @Test
    public void testParseNoParams() throws IOException {
        List<Benchmark> benchmarks = BenchmarkUtil.parseBenchmarks(BenchmarkUtil.RESULT_NO_PARAMS);
        // check we actually parsed something
        assertFalse(benchmarks.isEmpty());

        Benchmark benchmark = benchmarks.get(0);

        assertEquals(Collections.EMPTY_MAP, benchmark.getParams());

        Benchmark cloneBenchmark = new Benchmark(benchmark);
        assertEquals(Collections.EMPTY_MAP, cloneBenchmark.getParams());

    }

}
