package com.presidentio.teamcity.jmh.view;

import com.presidentio.teamcity.jmh.entity.Benchmark;
import com.presidentio.teamcity.jmh.entity.GroupedBenchmarks;
import junit.framework.TestCase;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by presidentio on 21.04.15.
 */
public class GroupedBechmarksTest extends TestCase {

    public void testGroping() throws Exception {
        List<Benchmark> benchmarks = new ArrayList<Benchmark>();
        Benchmark benchmark = new Benchmark();
        benchmark.setBenchmark("a.b");
        benchmarks.add(benchmark);
        benchmark = new Benchmark();
        benchmark.setBenchmark("b.b");
        benchmarks.add(benchmark);
        benchmark = new Benchmark();
        benchmark.setBenchmark("a.a");
        benchmarks.add(benchmark);
        benchmark = new Benchmark();
        benchmark.setBenchmark("a.c");
        benchmarks.add(benchmark);
        GroupedBenchmarks groupedBechmarks = new GroupedBenchmarks(benchmarks);
        Assert.assertEquals(2, groupedBechmarks.size());
        Assert.assertTrue(groupedBechmarks.containsKey("a"));
        Assert.assertTrue(groupedBechmarks.containsKey("b"));
        Assert.assertEquals(3, groupedBechmarks.get("a").size());
        Assert.assertEquals(1, groupedBechmarks.get("b").size());
    }
}