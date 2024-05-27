package com.antonigari.GrpcServerService.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class DeviceMeasurementRequestDto {
    Long deviceId;
    Long measurementTypeId;
    String measurementName;
    String units;
}
