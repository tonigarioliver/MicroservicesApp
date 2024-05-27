package com.antonigari.GrpcServerService.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Jacksonized
@Builder
public class DeviceModelRequestDto {
    @NotBlank
    String name;
    @NotBlank
    String serialNumber;
}
