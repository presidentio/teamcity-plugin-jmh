package com.presidentio.teamcity.jmh.entity;

import java.util.Map;

/**
 * Created by Argonaught on 19/06/2016.
 */
public class BenchmarksByParameter extends BaseBenchmarkGroup<Benchmark> {

    public static final String NO_PARAMS = "no-parameteres";

    private String scoreUnit;

    public BenchmarksByParameter() {
    }

    public BenchmarksByParameter(String scoreUnit) {
        this.scoreUnit = scoreUnit;
    }

    public void add(Benchmark benchmark) {
        if (scoreUnit == null) {
            scoreUnit = benchmark.getPrimaryMetric().getScoreUnit();
        } else {
            benchmark = changeScoreUnit(benchmark, scoreUnit);
        }
        String params = extractParamsString(benchmark.getParams());
        put(params, benchmark);
    }

    public static String extractParamsString(Map<String, String> params) {
        if( params.isEmpty() ) {
            return NO_PARAMS;
        } else {
            String paramString = "";
            for(Map.Entry<String, String> param : params.entrySet()) {
                paramString += param.getKey() + ":_" + param.getValue() + "_";
            }
            return paramString;
        }
    }

}
