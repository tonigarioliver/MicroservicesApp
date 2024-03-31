package com.example.mqttclient.data.model;

import lombok.Builder;

@Builder
public record DeviceMeasurementPayloadDto(Long deviceMeasurementPayloadId, String stringValue, Float numValue,
                                          Boolean booleanValue) {
}
