package com.antonigari.grpc.server.service.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class NewCompanyRequestDto {
    @NotBlank
    String name;
    @NotBlank
    String address;
    @NotBlank
    String phoneNumber;
}
