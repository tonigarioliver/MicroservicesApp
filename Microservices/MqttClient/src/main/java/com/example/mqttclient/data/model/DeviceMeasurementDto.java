package com.example.mqttclient.data.model;

import lombok.Builder;

@Builder
public record DeviceMeasurementDto(
        Long deviceMeasurementId,
        DeviceDto device,
        MeasurementTypeDto measurementType,
        String topic,
        String name,
        String unit
) {
}