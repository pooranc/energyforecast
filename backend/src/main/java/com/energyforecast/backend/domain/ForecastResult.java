package com.energyforecast.backend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
public class ForecastResult {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;


    private String source;

    private Instant createdAt;

    private int horizonHours;

    @ElementCollection
    private List<Double> predictedValues;


    private String modelVersion;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public int getHorizonHours() {
        return horizonHours;
    }

    public void setHorizonHours(int horizonHours) {
        this.horizonHours = horizonHours;
    }

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
