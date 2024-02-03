package com.antonigari.iotdeviceservice.model;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Value
@Builder
@Jacksonized
public class UpdateDeviceRequestDto {
    LocalDateTime manufactureDate;
    BigDecimal price;

    String manufactureCode;
}
