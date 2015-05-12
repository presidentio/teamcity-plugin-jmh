package com.presidentio.teamcity.jmh.runner.common.param;

/**
 * Created by presidentio on 12.05.15.
 */
public interface RunnerParam {

    boolean isRequired();
    String getName();
    String getCommandLineName();
    String getShortDescription();
    String getDescription();
    String validate(String value);
    int getType();

}
