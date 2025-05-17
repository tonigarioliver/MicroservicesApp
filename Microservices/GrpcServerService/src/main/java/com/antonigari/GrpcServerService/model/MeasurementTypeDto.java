package com.antonigari.GrpcServerService.model;

import com.antonigari.GrpcServerService.data.model.MeasurementTypeName;
import lombok.Builder;


@Builder
public record MeasurementTypeDto(Long measurementTypeId, MeasurementTypeName typeName) {
}
