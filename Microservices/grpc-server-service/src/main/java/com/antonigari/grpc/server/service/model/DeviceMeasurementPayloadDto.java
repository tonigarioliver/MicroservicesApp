package com.antonigari.grpc.server.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@AllArgsConstructor
@Builder
@Value
public class DeviceMeasurementPayloadDto {
    Long deviceMeasurementPayloadId;
    String stringValue;
    Float numValue;
    Boolean booleanValue;
}
