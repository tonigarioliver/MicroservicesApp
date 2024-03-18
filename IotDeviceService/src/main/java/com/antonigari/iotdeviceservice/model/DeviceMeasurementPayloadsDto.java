package com.antonigari.iotdeviceservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.util.List;

@Value
@Builder
@AllArgsConstructor
public class DeviceMeasurementPayloadsDto {
    @Singular
    List<DeviceMeasurementPayloadDto> deviceMeasurementPayloads;
}
