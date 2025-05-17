package com.antonigari.iotdeviceservice.data.repository;

import com.antonigari.iotdeviceservice.data.model.DeviceModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeviceModelRepository extends JpaRepository<DeviceModel, Long> {
    Optional<DeviceModel> findBySerialNumber(String serialNumber);
}
