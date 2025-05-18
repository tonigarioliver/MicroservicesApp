package com.antonigari.grpc.client.service.impl;

import com.antonigari.grpc.client.model.DeviceDto;
import com.antonigari.grpc.client.model.DeviceMeasurementDto;
import com.antonigari.grpc.client.model.MeasurementTypeDto;
import com.antonigari.grpc.client.model.MeasurementTypeName;
import com.antonigari.grpc.client.service.IDeviceMeasurementService;
import com.antonigari.grpc.server.service.DeviceMeasurementGrpc;
import com.antonigari.grpc.server.service.DeviceMeasurementGrpcServiceGrpc;
import com.antonigari.grpc.server.service.GetAllDeviceMeasurementRequest;
import com.antonigari.grpc.server.service.GetAllDeviceMeasurementResponse;
import com.antonigari.grpc.server.service.MeasurementTypeNameGrpc;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of IDeviceMeasurementService that uses gRPC to communicate with the device measurement service.
 * This is a shared implementation that can be used by multiple services.
 */
@Service
@Slf4j
@AllArgsConstructor
public class DeviceMeasurementGrpcServiceImpl implements IDeviceMeasurementService {

    private final DeviceMeasurementGrpcServiceGrpc.DeviceMeasurementGrpcServiceBlockingStub measurementStub;

    @Override
    public List<DeviceMeasurementDto> getAllDeviceMeasurement() {
        log.info("Retrieving all device measurements from gRPC service");
        final GetAllDeviceMeasurementResponse response = this.measurementStub
                .getAllDeviceMeasurement(GetAllDeviceMeasurementRequest.newBuilder().build());
        log.info(response.toString());
        return response.getDeviceMeasurementList().stream()
                .map(this::buildDeviceMeasurementDto)
                .toList();
    }

    /**
     * Builds a device measurement DTO from a gRPC device measurement message.
     *
     * @param measurement the gRPC device measurement message
     * @return the device measurement DTO
     */
    private DeviceMeasurementDto buildDeviceMeasurementDto(final DeviceMeasurementGrpc measurement) {
        return DeviceMeasurementDto.builder()
                .deviceMeasurementId(measurement.getDeviceMeasurementId())
                .topic(measurement.getTopic())
                .device(this.buildDeviceDto(measurement))
                .measurementType(this.buildMeasurementTypeDto(measurement))
                .build();
    }

    /**
     * Builds a device DTO from a gRPC device measurement message.
     *
     * @param measurement the gRPC device measurement message
     * @return the device DTO
     */
    private DeviceDto buildDeviceDto(final DeviceMeasurementGrpc measurement) {
        return DeviceDto.builder()
                .deviceId(measurement.getDevice().getDeviceId())
                .manufactureCode(measurement.getDevice().getManufactureCode())
                .build();
    }

    /**
     * Builds a measurement type DTO from a gRPC device measurement message.
     *
     * @param measurement the gRPC device measurement message
     * @return the measurement type DTO
     */
    private MeasurementTypeDto buildMeasurementTypeDto(final DeviceMeasurementGrpc measurement) {
        return MeasurementTypeDto.builder()
                .measurementTypeId(measurement.getMeasurementType().getMeasurementTypeId())
                .typeName(this.convertMeasurementTypeName(measurement.getMeasurementType().getTypeName()))
                .build();
    }

    /**
     * Converts a gRPC measurement type name enum to a measurement type name enum.
     *
     * @param type the gRPC measurement type name enum
     * @return the measurement type name enum
     */
    private MeasurementTypeName convertMeasurementTypeName(final MeasurementTypeNameGrpc type) {
        return switch (type) {
            case STRING -> MeasurementTypeName.STRING;
            case BOOLEAN -> MeasurementTypeName.BOOLEAN;
            case NUMERIC -> MeasurementTypeName.NUMERIC;
            case UNRECOGNIZED -> throw new UnsupportedOperationException("Type name is not implemented yet");
        };
    }
}