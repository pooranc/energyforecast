package com.energyforecast.backend.api;

import com.energyforecast.backend.domain.EnergyReading;
import com.energyforecast.backend.repository.EnergyReadingRepository;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/readings")
public class ReadingController {

    private final EnergyReadingRepository repository;

    public ReadingController(EnergyReadingRepository repository) {  // ← not here
        this.repository = repository;
    }

    @GetMapping
    public List<EnergyReading> getAllReading() {
        return repository.findAll();
    }

    @PostMapping
    public EnergyReading create(@RequestBody EnergyReading reading) {
        reading.setTimestamp(Instant.now());
        return repository.save(reading);
    }
}
