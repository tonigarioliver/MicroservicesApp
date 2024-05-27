package com.antonigari.GrpcServerService.model;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
public class CompaniesDto {
    @Singular
    List<CompanyDto> companies;
}
