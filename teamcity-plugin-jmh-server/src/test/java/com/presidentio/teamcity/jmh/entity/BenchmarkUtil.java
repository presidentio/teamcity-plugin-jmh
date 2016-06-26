package com.presidentio.teamcity.jmh.entity;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by Argonaught on 19/06/2016.
 */
public class BenchmarkUtil {
    public static final String RESULT_WITH_PARAMS = "jmh-result-with-params.json";
    public static final String RESULT_NO_PARAMS = "jmh-result-no-params.json";

    public static List<Benchmark> parseBenchmarks(String JsonResultsResource) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Benchmark> benchmarks = objectMapper.readValue(new File(BenchmarkUtil.class.getResource(JsonResultsResource).getFile()), objectMapper.getTypeFactory().constructCollectionType(List.class, Benchmark.class));
        return benchmarks;

    }
}
