package com.antonigari.iotdeviceservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.util.List;

@Builder
@Value
@AllArgsConstructor
public class DeviceMeasurementDetailsDto {
    Long deviceMeasurementId;
    DeviceDto device;
    MeasurementTypeDto measurementType;
    String topic;
    String name;
    String unit;
    @Singular
    List<DeviceMeasurementPayloadDto> measures;
}
