package com.antonigari.iotdeviceservice.model;

import lombok.Builder;

@Builder
public record DeviceMeasurementDto(Long deviceMeasurementId, DeviceDto device, MeasurementTypeDto measurementType,
                                   String topic, String name, String unit) {
}
