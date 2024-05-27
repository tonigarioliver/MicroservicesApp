package com.antonigari.GrpcServerService.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class CompanyDto {
    @NotBlank
    String name;
    @NotBlank
    String address;
    @NotBlank
    String phoneNumber;
}
