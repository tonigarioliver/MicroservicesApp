package com.antonigari.iotdeviceservice.model;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
public class PhysicalMeasurementsDto {
    @Singular
    List<PhysicalMeasurementDto> physicalMeasurements;
}
