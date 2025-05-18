package com.antonigari.grpcclient.model;

import lombok.Builder;

/**
 * Data Transfer Object for device measurements.
 */
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