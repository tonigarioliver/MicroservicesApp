package com.antonigari.mqtt.client.data.model;

import lombok.Builder;

@Builder
public record DeviceMeasurementPayloadDto(Long deviceMeasurementId, String topic, Long deviceMeasurementPayloadId,
                                          String stringValue, Float numValue,
                                          Boolean booleanValue) {
}