package com.antonigari.iotdeviceservice.model;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
@Builder
@Jacksonized
public class DeviceDto {
     Long deviceId;
     String manufactureCode;

     DeviceModelDto deviceModel;

     LocalDateTime manufactureDate;
     BigDecimal price;
}
