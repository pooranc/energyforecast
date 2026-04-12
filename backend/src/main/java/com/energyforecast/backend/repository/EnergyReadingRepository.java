package com.energyforecast.backend.repository;

import com.energyforecast.backend.domain.EnergyReading;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EnergyReadingRepository extends JpaRepository<EnergyReading, UUID> {
}
