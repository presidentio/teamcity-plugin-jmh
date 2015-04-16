package com.presidentio.teamcity.jmh.entity;

import java.util.Arrays;
import java.util.Map;

/**
 * Created by Vitaliy on 16.04.2015.
 */
public class PrimaryMetric {
    
    public Double score;
    public Double scoreError;
    public Double[] scoreConfidence;
    public Map<String, Double> scorePercentiles;
    public String scoreUnit;
    public Double[][] rawData;

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Double getScoreError() {
        return scoreError;
    }

    public void setScoreError(Double scoreError) {
        this.scoreError = scoreError;
    }

    public Double[] getScoreConfidence() {
        return scoreConfidence;
    }

    public void setScoreConfidence(Double[] scoreConfidence) {
        this.scoreConfidence = scoreConfidence;
    }

    public Map<String, Double> getScorePercentiles() {
        return scorePercentiles;
    }

    public void setScorePercentiles(Map<String, Double> scorePercentiles) {
        this.scorePercentiles = scorePercentiles;
    }

    public String getScoreUnit() {
        return scoreUnit;
    }

    public void setScoreUnit(String scoreUnit) {
        this.scoreUnit = scoreUnit;
    }

    public Double[][] getRawData() {
        return rawData;
    }

    public void setRawData(Double[][] rawData) {
        this.rawData = rawData;
    }

    @Override
    public String toString() {
        return "PrimaryMetric{" +
                "score=" + score +
                ", scoreError=" + scoreError +
                ", scoreConfidence=" + Arrays.toString(scoreConfidence) +
                ", scorePercentiles=" + scorePercentiles +
                ", scoreUnit='" + scoreUnit + '\'' +
                ", rawData=" + Arrays.toString(rawData) +
                '}';
    }
}
