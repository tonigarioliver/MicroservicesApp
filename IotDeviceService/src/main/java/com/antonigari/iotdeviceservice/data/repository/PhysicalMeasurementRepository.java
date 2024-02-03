package com.antonigari.iotdeviceservice.data.repository;

import com.antonigari.iotdeviceservice.data.model.PhysicalMeasurement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PhysicalMeasurementRepository extends JpaRepository<PhysicalMeasurement, Long> {
    Optional<PhysicalMeasurement> findByName(String name);
}
