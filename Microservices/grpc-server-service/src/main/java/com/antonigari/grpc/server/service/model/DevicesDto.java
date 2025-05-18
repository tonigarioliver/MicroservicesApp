package com.antonigari.grpc.server.service.model;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
public class DevicesDto {
    @Singular
    List<DeviceDto> devices;
}
