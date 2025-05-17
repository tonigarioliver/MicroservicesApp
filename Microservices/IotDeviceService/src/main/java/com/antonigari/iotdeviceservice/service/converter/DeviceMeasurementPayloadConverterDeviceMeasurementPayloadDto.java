package com.antonigari.iotdeviceservice.service.converter;

import com.antonigari.iotdeviceservice.data.model.DeviceMeasurementPayload;
import com.antonigari.iotdeviceservice.model.DeviceMeasurementPayloadDto;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DeviceMeasurementPayloadConverterDeviceMeasurementPayloadDto
        implements Converter<DeviceMeasurementPayload, DeviceMeasurementPayloadDto> {
    @Override
    @NonNull
    public DeviceMeasurementPayloadDto convert(final DeviceMeasurementPayload payload) {
        return DeviceMeasurementPayloadDto.builder()
                .deviceMeasurementPayloadId(payload.getDeviceMeasurementPayloadId())
                .booleanValue(payload.getBooleanValue())
                .stringValue(payload.getStringValue())
                .numValue(payload.getNumValue())
                .build();
    }
}
