package com.example.mqttclient.data.model;

import lombok.Builder;

@Builder
public record MeasurementTypeDto(Long measurementTypeId, MeasurementTypeName typeName) {
}
