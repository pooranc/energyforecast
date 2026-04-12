package com.energyforecast.backend.service;

import com.energyforecast.backend.domain.EnergyReading;
import com.energyforecast.backend.repository.EnergyReadingRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class ReadingService {

    private final EnergyReadingRepository repository;

    public ReadingService(EnergyReadingRepository repository) {
        this.repository = repository;
    }

    public EnergyReading save(EnergyReading reading) {
        reading.setTimestamp(Instant.now());
        return repository.save(reading);
    }

    public List<EnergyReading> findAll() {
        return repository.findAll();
    }
}
