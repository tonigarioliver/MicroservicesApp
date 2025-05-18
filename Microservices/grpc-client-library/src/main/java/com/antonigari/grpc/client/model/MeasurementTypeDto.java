package com.antonigari.grpc.client.model;

import lombok.Builder;

/**
 * Data Transfer Object for measurement types.
 */
@Builder
public record MeasurementTypeDto(Long measurementTypeId, MeasurementTypeName typeName) {
}