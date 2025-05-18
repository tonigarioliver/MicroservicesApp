package com.antonigari.grpc.server.service.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Jacksonized
@Builder
public class DeviceModelDto {
    long deviceModelId;
    @NotBlank
    String name;
    @NotBlank
    String serialNumber;
}
