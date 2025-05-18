package com.antonigari.grpc.server.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.util.List;

@Builder
@Value
@AllArgsConstructor
public class MeasurementTypesDto {
    @Singular
    List<MeasurementTypeDto> measurementTypes;
}
