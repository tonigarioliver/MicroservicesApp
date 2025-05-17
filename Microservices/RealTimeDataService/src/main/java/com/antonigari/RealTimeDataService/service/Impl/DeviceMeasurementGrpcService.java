package com.antonigari.RealTimeDataService.service.Impl;

import com.antonigari.RealTimeDataService.DeviceMeasurementGrpc;
import com.antonigari.RealTimeDataService.DeviceMeasurementGrpcServiceGrpc;
import com.antonigari.RealTimeDataService.GetAllDeviceMeasurementRequest;
import com.antonigari.RealTimeDataService.GetAllDeviceMeasurementResponse;
import com.antonigari.RealTimeDataService.MeasurementTypeNameGrpc;
import com.antonigari.RealTimeDataService.model.DeviceDto;
import com.antonigari.RealTimeDataService.model.DeviceMeasurementDto;
import com.antonigari.RealTimeDataService.model.MeasurementTypeDto;
import com.antonigari.RealTimeDataService.model.MeasurementTypeName;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class DeviceMeasurementGrpcService {

    private final DeviceMeasurementGrpcServiceGrpc.DeviceMeasurementGrpcServiceBlockingStub measurementStub;

    /**
     * Retrieves all device measurements from the gRPC service.
     *
     * @return List of device measurements
     */
    public List<DeviceMeasurementDto> getAllDeviceMeasurement() {
        final GetAllDeviceMeasurementResponse response = this.measurementStub
                .getAllDeviceMeasurement(GetAllDeviceMeasurementRequest.newBuilder().build());
        log.info(response.toString());
        return response.getDeviceMeasurementList().stream()
                .map(this::buildDeviceMeasurementDto)
                .toList();
    }

    private DeviceMeasurementDto buildDeviceMeasurementDto(final DeviceMeasurementGrpc measurement) {
        return DeviceMeasurementDto.builder()
                .deviceMeasurementId(measurement.getDeviceMeasurementId())
                .topic(measurement.getTopic())
                .device(this.buildDeviceDto(measurement))
                .measurementType(this.buildMeasurementTypeDto(measurement))
                .build();
    }

    private DeviceDto buildDeviceDto(final DeviceMeasurementGrpc measurement) {
        return DeviceDto.builder()
                .deviceId(measurement.getDevice().getDeviceId())
                .manufactureCode(measurement.getDevice().getManufactureCode())
                .build();
    }

    private MeasurementTypeDto buildMeasurementTypeDto(final DeviceMeasurementGrpc measurement) {
        return MeasurementTypeDto.builder()
                .measurementTypeId(measurement.getMeasurementType().getMeasurementTypeId())
                .typeName(this.convertMeasurementTypeName(measurement.getMeasurementType().getTypeName()))
                .build();
    }

    private MeasurementTypeName convertMeasurementTypeName(final MeasurementTypeNameGrpc type) {
        return switch (type) {
            case STRING -> MeasurementTypeName.STRING;
            case BOOLEAN -> MeasurementTypeName.BOOLEAN;
            case NUMERIC -> MeasurementTypeName.NUMERIC;
            case UNRECOGNIZED -> throw new UnsupportedOperationException("Type name is not implemented yet");
        };
    }
}