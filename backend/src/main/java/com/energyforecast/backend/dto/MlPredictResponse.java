package com.energyforecast.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MlPredictResponse {
    @JsonProperty("predicted_values")
    private List<Double> predictedValues;
    @JsonProperty("model_version")
    private String modelVersion;

    public List<Double> getPredictedValues() {
        return predictedValues;
    }

    public void setPredictedValues(List<Double> predictedValues) {
        this.predictedValues = predictedValues;
    }

    public String getModelVersion() {
        return modelVersion;
    }

    public void setModelVersion(String modelVersion) {
        this.modelVersion = modelVersion;
    }
}
