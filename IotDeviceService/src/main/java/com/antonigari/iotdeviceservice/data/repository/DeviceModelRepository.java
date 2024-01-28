package com.antonigari.iotdeviceservice.data.repository;

import com.antonigari.iotdeviceservice.data.model.DeviceModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceModelRepository extends JpaRepository<DeviceModel, Long> {
    DeviceModel findBySerialNumber(String serialNumber);
}
