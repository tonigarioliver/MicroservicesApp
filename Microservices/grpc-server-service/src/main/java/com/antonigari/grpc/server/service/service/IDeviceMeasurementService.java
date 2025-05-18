package com.antonigari.grpc.server.service.service;

import com.antonigari.grpc.server.service.model.DeviceMeasurementDto;
import com.antonigari.grpc.server.service.model.DeviceMeasurementRequestDto;
import com.antonigari.grpc.server.service.model.DeviceMeasurementsDto;

public interface IDeviceMeasurementService extends ICrudService<
        DeviceMeasurementDto,
        DeviceMeasurementRequestDto,
        DeviceMeasurementRequestDto,
        DeviceMeasurementsDto> {
}