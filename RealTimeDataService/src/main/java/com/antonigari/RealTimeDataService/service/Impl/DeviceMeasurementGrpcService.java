package com.antonigari.RealTimeDataService.service.Impl;

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
    private final DeviceMeasurementGrpcServiceGrpc.DeviceMeasurementGrpcServiceBlockingStub deviceMeasurementGrpcServiceBlockingStub;

    public List<DeviceMeasurementDto> getAllDeviceMeasurement() {
        final GetAllDeviceMeasurementResponse response = this.deviceMeasurementGrpcServiceBlockingStub
                .getAllDeviceMeasurement(GetAllDeviceMeasurementRequest.newBuilder().build());
        log.info(response.toString());
        return response.getDeviceMeasurementList().stream()
                .map(meassure -> DeviceMeasurementDto.builder()
                        .deviceMeasurementId(meassure.getDeviceMeasurementId())
                        .topic(meassure.getTopic())
                        .device(DeviceDto.builder()
                                .deviceId(meassure.getDevice().getDeviceId())
                                .manufactureCode(meassure.getDevice().getManufactureCode())
                                .build())
                        .measurementType(MeasurementTypeDto.builder()
                                .measurementTypeId(meassure.getMeasurementType().getMeasurementTypeId())
                                .typeName(this.getGrpcMeasurementTypeName(meassure.getMeasurementType().getTypeName()))
                                .build())
                        .build())
                .toList();
    }

    private MeasurementTypeName getGrpcMeasurementTypeName(final MeasurementTypeNameGrpc type) {
        switch (type) {
            case STRING -> {
                return MeasurementTypeName.STRING;
            }
            case BOOLEAN -> {
                return MeasurementTypeName.BOOLEAN;
            }
            case NUMERIC -> {
                return MeasurementTypeName.NUMERIC;
            }
        }
        throw new UnsupportedOperationException("type name is not implemented yet");
    }
}
