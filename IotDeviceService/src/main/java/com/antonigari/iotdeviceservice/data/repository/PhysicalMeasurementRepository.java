package com.antonigari.iotdeviceservice.data.repository;

import com.antonigari.iotdeviceservice.data.model.PhysicalMeasurement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhysicalMeasurementRepository extends JpaRepository<PhysicalMeasurement, Long> {
    PhysicalMeasurement findByName(String name);
}
