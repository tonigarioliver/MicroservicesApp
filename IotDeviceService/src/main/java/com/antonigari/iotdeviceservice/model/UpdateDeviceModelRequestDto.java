package com.antonigari.iotdeviceservice.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;


@Value
@Jacksonized
@Builder
public class UpdateDeviceModelRequestDto {
    @NotBlank
    String name;
    @NotBlank
    String serialNumber;
}
