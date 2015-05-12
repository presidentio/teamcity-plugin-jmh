package com.presidentio.teamcity.jmh.runner.common;

/**
 * Created by presidentio on 12.05.15.
 */
public class RunnerParamTypeConst {

    public static final int STRING = 1;
    public static final int INT = 2;
    public static final int BOOL = 3;
    public static final int STRING_SELECT = 4;

    public int getSTRING() {
        return STRING;
    }

    public int getINT() {
        return INT;
    }

    public int getBOOL() {
        return BOOL;
    }

    public int getSTRING_SELECT() {
        return STRING_SELECT;
    }
}
