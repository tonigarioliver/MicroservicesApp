package com.antonigari.iotdeviceservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@AllArgsConstructor
@Builder
@Value
public class DeviceMeasurementPayloadDto {
    Long deviceMeasurementId;
    String stringValue;
    Float numValue;
    Boolean booleanValue;
}
