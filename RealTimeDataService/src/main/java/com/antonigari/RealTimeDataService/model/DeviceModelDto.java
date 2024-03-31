package com.antonigari.RealTimeDataService.model;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Jacksonized
@Builder
public class DeviceModelDto {
    long deviceModelId;
    String name;
    String serialNumber;
}