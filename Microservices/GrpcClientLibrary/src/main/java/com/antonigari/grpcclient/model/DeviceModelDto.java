package com.antonigari.grpcclient.model;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * Data Transfer Object for device models.
 */
@Value
@Jacksonized
@Builder
public class DeviceModelDto {
    long deviceModelId;
    String name;
    String serialNumber;
}