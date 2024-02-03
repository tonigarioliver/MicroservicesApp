package com.antonigari.iotdeviceservice.data.repository;
import com.antonigari.iotdeviceservice.data.model.DeviceMeasurement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeviceMeasurementRepository extends JpaRepository<DeviceMeasurement, Long> {
    Optional<DeviceMeasurement> findByIotTopic(String iotTopic);
}
