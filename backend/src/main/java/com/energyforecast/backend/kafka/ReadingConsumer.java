package com.energyforecast.backend.kafka;

import com.energyforecast.backend.domain.EnergyReading;
import com.energyforecast.backend.service.ForecastService;
import com.energyforecast.backend.service.ReadingService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ReadingConsumer {

    private final ForecastService forecastService;
    private final ReadingService readingService;

    private final Map<String, List<EnergyReading>> buffer = new ConcurrentHashMap<>();

    public ReadingConsumer(ForecastService forecastService, ReadingService readingService) {
        this.forecastService = forecastService;
        this.readingService = readingService;
    }

    @KafkaListener(topics = "sensor-readings", groupId = "energyforecast-group-v2")
    public void consume(EnergyReading reading) {
        buffer.computeIfAbsent(reading.getSource(), k -> new ArrayList<>()).add(reading);

        List<EnergyReading> sourceReadings = buffer.get(reading.getSource());
        System.out.println("Buffer size for " + reading.getSource() + ": " + sourceReadings.size());

        if (sourceReadings.size() >= 5) {
            System.out.println("Triggering forecast for: " + reading.getSource());
            forecastService.forecast(reading.getSource(), sourceReadings);
            buffer.remove(reading.getSource());
        }
    }
}
