package com.energyforecast.backend.api;

import com.energyforecast.backend.domain.ForecastResult;
import com.energyforecast.backend.repository.ForecastResultRepository;
import com.energyforecast.backend.service.ForecastService;
import com.energyforecast.backend.service.ReadingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/forecast")
public class ForecastController {

    private final ForecastResultRepository repository;

    public ForecastController(ForecastResultRepository repository) {  //
        this.repository = repository;
    }

    @GetMapping
    public List<ForecastResult> getForecast(@RequestParam String source) {
        return repository.findBySourceOrderByCreatedAtDesc(source);
    }
}
