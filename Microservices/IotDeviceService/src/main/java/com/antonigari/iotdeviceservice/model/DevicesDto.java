package com.antonigari.iotdeviceservice.model;

import lombok.*;
import lombok.extern.jackson.*;

import java.util.*;

@Value
@Builder
@Jacksonized
public class DevicesDto {
    @Singular
    List<DeviceDto> devices;
}
