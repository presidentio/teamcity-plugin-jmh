package com.presidentio.teamcity.jmh.runner.common;

import java.util.concurrent.TimeUnit;

/**
 * Created by Vitaliy on 04.05.2015.
 */
public class UnitConverter {
    
    public static double convert(double value, String unitFrom, String unitTo) {
        return value * (getMultiplier(unitFrom) / getMultiplier(unitTo));
    }

    private static double getMultiplier(String unit) {
        switch (unit) {
            case ScoreUnitConst.MIN_OP:
                return TimeUnit.MINUTES.toNanos(1);
            case ScoreUnitConst.S_OP:
                return TimeUnit.SECONDS.toNanos(1);
            case ScoreUnitConst.MS_OP:
                return TimeUnit.MILLISECONDS.toNanos(1);
            case ScoreUnitConst.US_OP:
                return TimeUnit.MICROSECONDS.toNanos(1);
            case ScoreUnitConst.NS_OP:
                return 1D;
            case ScoreUnitConst.OPS_MIN:
                return 1d / TimeUnit.MINUTES.toNanos(1);
            case ScoreUnitConst.OPS_S:
                return 1D / TimeUnit.SECONDS.toNanos(1);
            case ScoreUnitConst.OPS_MS:
                return 1D / TimeUnit.MILLISECONDS.toNanos(1);
            case ScoreUnitConst.OPS_US:
                return 1D / TimeUnit.MICROSECONDS.toNanos(1);
            case ScoreUnitConst.OPS_NS:
                return 1D;
            default:
                throw new IllegalArgumentException("Unknown score unit: " + unit);
        }
    }

}
