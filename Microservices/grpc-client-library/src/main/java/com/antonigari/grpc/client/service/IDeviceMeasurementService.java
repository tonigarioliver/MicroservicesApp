package com.antonigari.grpc.client.service;

import com.antonigari.grpc.client.model.DeviceMeasurementDto;

import java.util.List;

/**
 * Interface for device measurement services.
 * Defines common operations for retrieving device measurements.
 */
public interface IDeviceMeasurementService {

    /**
     * Retrieves all device measurements from the gRPC service.
     *
     * @return List of device measurements
     */
    List<DeviceMeasurementDto> getAllDeviceMeasurement();
}
