package com.antonigari.iotdeviceservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.util.List;

@AllArgsConstructor
@Builder
@Value
public class DeviceMeasurementsDto {
    @Singular
    List<DeviceMeasurementDto> deviceMeasurements;
}
