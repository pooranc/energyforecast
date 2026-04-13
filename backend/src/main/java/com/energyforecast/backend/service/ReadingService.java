package com.energyforecast.backend.service;

import com.energyforecast.backend.domain.EnergyReading;
import com.energyforecast.backend.kafka.ReadingProducer;
import com.energyforecast.backend.repository.EnergyReadingRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class ReadingService {

    private final EnergyReadingRepository repository;
    private final ReadingProducer producer;

    public ReadingService(EnergyReadingRepository repository, ReadingProducer producer) {
        this.repository = repository;
        this.producer = producer;
    }

    public EnergyReading save(EnergyReading reading) {
        reading.setTimestamp(Instant.now());
        var energyReading = repository.save(reading);
        producer.publish(reading);
        return energyReading;
    }

    public List<EnergyReading> findAll() {
        return repository.findAll();
    }
}
