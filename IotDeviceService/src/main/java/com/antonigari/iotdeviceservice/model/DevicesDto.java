package com.antonigari.iotdeviceservice.model;

import java.util.List;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class DevicesDto {
    List<DeviceDto> devices;
}
