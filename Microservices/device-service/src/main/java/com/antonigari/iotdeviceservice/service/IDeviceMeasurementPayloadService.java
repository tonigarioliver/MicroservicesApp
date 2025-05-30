package com.antonigari.iotdeviceservice.service;

import com.antonigari.iotdeviceservice.model.DeviceMeasurementPayloadDto;
import com.antonigari.iotdeviceservice.model.DeviceMeasurementPayloadRequestDto;
import com.antonigari.iotdeviceservice.model.DeviceMeasurementPayloadsDto;

public interface IDeviceMeasurementPayloadService extends ICrudService<
        DeviceMeasurementPayloadDto,
        DeviceMeasurementPayloadRequestDto,
        DeviceMeasurementPayloadRequestDto,
        DeviceMeasurementPayloadsDto> {
}
