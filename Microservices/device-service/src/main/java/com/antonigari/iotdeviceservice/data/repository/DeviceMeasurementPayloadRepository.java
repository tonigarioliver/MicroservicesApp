package com.antonigari.iotdeviceservice.data.repository;

import com.antonigari.iotdeviceservice.data.model.DeviceMeasurementPayload;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceMeasurementPayloadRepository extends JpaRepository<DeviceMeasurementPayload, Long> {
}
