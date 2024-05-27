package com.antonigari.GrpcServerService.data.repository;


import com.antonigari.GrpcServerService.data.model.DeviceMeasurement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeviceMeasurementRepository extends JpaRepository<DeviceMeasurement, Long> {
    Optional<DeviceMeasurement> findByTopic(String topic);
}
