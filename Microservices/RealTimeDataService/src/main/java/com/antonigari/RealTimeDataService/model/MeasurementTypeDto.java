package com.antonigari.RealTimeDataService.model;

import lombok.Builder;

@Builder
public record MeasurementTypeDto(Long measurementTypeId, MeasurementTypeName typeName) {
}
