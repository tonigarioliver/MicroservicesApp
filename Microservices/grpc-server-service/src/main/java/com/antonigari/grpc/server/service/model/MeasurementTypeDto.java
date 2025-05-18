package com.antonigari.grpc.server.service.model;

import com.antonigari.grpc.server.service.data.model.MeasurementTypeName;
import lombok.Builder;


@Builder
public record MeasurementTypeDto(Long measurementTypeId, MeasurementTypeName typeName) {
}
