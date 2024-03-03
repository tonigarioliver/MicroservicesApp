package com.antonigari.iotdeviceservice.data.repository;

import com.antonigari.iotdeviceservice.data.model.DeviceTopic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeviceTopicRepository extends JpaRepository<DeviceTopic, Long> {
    Optional<DeviceTopic> findByDeviceManufactureCode(String deviceManufactureCode);
}
