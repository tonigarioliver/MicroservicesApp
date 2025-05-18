package com.antonigari.grpc.client.service.impl;

import com.antonigari.grpc.client.model.DeviceDto;
import com.antonigari.grpc.client.model.DeviceMeasurementDto;
import com.antonigari.grpc.client.model.MeasurementTypeDto;
import com.antonigari.grpc.client.model.MeasurementTypeName;
import com.antonigari.grpc.client.service.IDeviceMeasurementService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Abstract base class for device measurement gRPC services.
 * Provides common functionality for retrieving device measurements from gRPC services.
 *
 * @param <G> the type of gRPC device measurement message
 * @param <E> the type of gRPC measurement type name enum
 */
@Slf4j
public abstract class AbstractDeviceMeasurementGrpcService<G, E> implements IDeviceMeasurementService {

    /**
     * Retrieves all device measurements from the gRPC service.
     *
     * @return List of device measurements
     */
    @Override
    public List<DeviceMeasurementDto> getAllDeviceMeasurement() {
        log.info("Retrieving all device measurements from gRPC service");
        return this.getDeviceMeasurementList().stream()
                .map(this::buildDeviceMeasurementDto)
                .toList();
    }

    /**
     * Gets the list of device measurements from the gRPC service.
     *
     * @return List of gRPC device measurement messages
     */
    protected abstract List<G> getDeviceMeasurementList();

    /**
     * Builds a device measurement DTO from a gRPC device measurement message.
     *
     * @param measurement the gRPC device measurement message
     * @return the device measurement DTO
     */
    protected abstract DeviceMeasurementDto buildDeviceMeasurementDto(G measurement);

    /**
     * Builds a device DTO from a gRPC device measurement message.
     *
     * @param measurement the gRPC device measurement message
     * @return the device DTO
     */
    protected abstract DeviceDto buildDeviceDto(G measurement);

    /**
     * Builds a measurement type DTO from a gRPC device measurement message.
     *
     * @param measurement the gRPC device measurement message
     * @return the measurement type DTO
     */
    protected abstract MeasurementTypeDto buildMeasurementTypeDto(G measurement);

    /**
     * Converts a gRPC measurement type name enum to a measurement type name enum.
     *
     * @param type the gRPC measurement type name enum
     * @return the measurement type name enum
     */
    protected abstract MeasurementTypeName convertMeasurementTypeName(E type);
}