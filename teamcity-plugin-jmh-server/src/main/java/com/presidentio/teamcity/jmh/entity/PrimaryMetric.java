/**
 * Copyright 2015 presidentio
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.presidentio.teamcity.jmh.entity;

import java.util.Arrays;
import java.util.HashMap;
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

    public PrimaryMetric(PrimaryMetric primaryMetric) {
        this.score = primaryMetric.getScore();
        this.scoreError = primaryMetric.getScore();
        this.scoreConfidence = Arrays.copyOf(primaryMetric.getScoreConfidence(), primaryMetric.getScoreConfidence().length);
        this.scorePercentiles = new HashMap<>(primaryMetric.getScorePercentiles());
        this.scoreUnit = primaryMetric.getScoreUnit();
        this.rawData = new Double[primaryMetric.getRawData().length][];
        for (int i = 0; i < rawData.length; i++) {
            rawData[i] = Arrays.copyOf(primaryMetric.getRawData()[i], primaryMetric.getRawData()[i].length);
        }
    }

    public PrimaryMetric() {
    }

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
