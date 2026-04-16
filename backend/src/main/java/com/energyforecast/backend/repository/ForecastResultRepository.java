package com.energyforecast.backend.repository;

import com.energyforecast.backend.domain.ForecastResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ForecastResultRepository extends JpaRepository<ForecastResult, UUID> {
}
