package com.antonigari.grpc.server.service.model;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
@Builder
@Jacksonized
public class DeviceRequestDto {
    Long deviceId;
    String manufactureCode;

    Long deviceModelId;

    LocalDateTime manufactureDate;
    BigDecimal price;
}
