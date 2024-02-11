package com.antonigari.iotdeviceservice.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
@Value
@Builder
@Jacksonized
public class PhysicalMeasurementRequestDto {
    Long physicalMeasurementId;
    @NotBlank
    String name;
    @NotBlank
    String unit;
}
