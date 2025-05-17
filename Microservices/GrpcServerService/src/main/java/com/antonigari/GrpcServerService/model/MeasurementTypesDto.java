package com.antonigari.GrpcServerService.model;

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
