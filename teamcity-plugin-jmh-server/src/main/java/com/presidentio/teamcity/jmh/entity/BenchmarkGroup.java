package com.presidentio.teamcity.jmh.entity;

/**
 * Created by Vitaliy on 06.05.2015.
 */
public interface BenchmarkGroup {
    
    double minTime();
    double maxTime();
    String scoreUnit();

}
