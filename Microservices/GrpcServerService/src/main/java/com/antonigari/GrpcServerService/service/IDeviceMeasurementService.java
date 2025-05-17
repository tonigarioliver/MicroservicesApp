package com.antonigari.GrpcServerService.service;

import com.antonigari.GrpcServerService.model.DeviceMeasurementDto;
import com.antonigari.GrpcServerService.model.DeviceMeasurementRequestDto;
import com.antonigari.GrpcServerService.model.DeviceMeasurementsDto;

public interface IDeviceMeasurementService extends ICrudService<
        DeviceMeasurementDto,
        DeviceMeasurementRequestDto,
        DeviceMeasurementRequestDto,
        DeviceMeasurementsDto> {
}