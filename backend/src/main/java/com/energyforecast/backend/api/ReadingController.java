package com.energyforecast.backend.api;

import com.energyforecast.backend.domain.EnergyReading;
import com.energyforecast.backend.service.ReadingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/readings")
public class ReadingController {

    private final ReadingService service;

    public ReadingController(ReadingService service) {  // ← not here
        this.service = service;
    }

    @GetMapping
    public List<EnergyReading> getAllReading() {
        return service.findAll();
    }

    @PostMapping
    public EnergyReading create(@RequestBody EnergyReading reading) {
        return service.save(reading);
    }
}
