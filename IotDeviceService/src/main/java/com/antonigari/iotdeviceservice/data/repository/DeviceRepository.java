package com.antonigari.iotdeviceservice.data.repository;

import com.antonigari.iotdeviceservice.data.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    Optional<Device> findByManufactureCode(String manufactureCode);
}
