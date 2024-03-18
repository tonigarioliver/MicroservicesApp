package com.antonigari.iotdeviceservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
public class DeviceMeasurementDto {
    Long deviceMeasurementId;
    DeviceDto device;
    MeasurementTypeDto measurementType;
    String topic;
}
