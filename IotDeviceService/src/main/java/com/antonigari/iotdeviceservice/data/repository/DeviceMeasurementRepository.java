package com.antonigari.iotdeviceservice.data.repository;
import com.antonigari.iotdeviceservice.data.model.DeviceMeasurement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceMeasurementRepository extends JpaRepository<DeviceMeasurement, Long> {
    DeviceMeasurement findByIotTopic(String iotTopic);
}
