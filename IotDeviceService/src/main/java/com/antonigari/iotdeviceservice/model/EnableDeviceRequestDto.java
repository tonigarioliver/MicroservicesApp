package com.antonigari.iotdeviceservice.model;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class EnableDeviceRequestDto {
    String manufactureCode;
    boolean enable;

}