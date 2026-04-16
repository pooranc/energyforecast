package com.energyforecast.backend.service;

import com.energyforecast.backend.domain.EnergyReading;
import com.energyforecast.backend.domain.ForecastResult;
import com.energyforecast.backend.dto.MlPredictResponse;
import com.energyforecast.backend.repository.ForecastResultRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ForecastService {

    private final ForecastResultRepository repository;
    private final RestTemplate restTemplate;

    @Value("${ml-service.base-url}")
    private String mlServiceUrl;

    public ForecastService(ForecastResultRepository repository, RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    public ForecastResult forecast(String source, List<EnergyReading> readings) {
        // 1. Build request body for the ML service
        Map<String, Object> request = new HashMap<>();
        request.put("source", source);
        request.put("horizon_hours", 24);
        request.put("readings", readings.stream()//
                .map(r -> Map.of("timestamp", r.getTimestamp().toString(), "value", r.getValue()))//
                .toList());

        // 2. Call ml service
        MlPredictResponse response = restTemplate.postForObject(
                mlServiceUrl + "/predict",
                request,
                MlPredictResponse.class
        );

        //3. Save forecast result
        ForecastResult result = new ForecastResult();
        result.setSource(source);
        result.setCreatedAt(Instant.now());
        result.setHorizonHours(24);
        result.setPredictedValues(response.getPredictedValues());
        result.setModelVersion(response.getModelVersion());

        return repository.save(result);
    }
}
